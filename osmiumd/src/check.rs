use crate::{storage, types};
use anyhow::anyhow;
use reqwest::header::{HeaderMap, HeaderValue, CONTENT_TYPE, USER_AGENT};
use serde_json::Value;
use std::env;

pub(crate) async fn periodic_check() {
    println!("Performing periodic check...");
    let mut servers = storage::load_servers();
    for (_, server) in servers.servers.iter_mut() {
        let resources = match fetch_server_resources(&server.id).await {
            Ok(resources) => resources,
            Err(_) => server.resources.clone(),
        };
        let players = match fetch_plan_players(&server.name).await {
            Ok(players) => players,
            Err(_) => server.players.clone(),
        };

        server.resources = resources;
        server.players = players;
    }
    storage::save_servers(&servers);
}

async fn fetch_server_resources(
    server_id: &String,
) -> Result<types::ServerResources, anyhow::Error> {
    let client = reqwest::Client::new();
    let url = format!(
        "http://192.168.120.104/api/client/servers/{}/resources",
        server_id
    );

    // Handle API key retrieval more safely
    let apikey = env::var("PTERO_APIKEY")
        .map_err(|_| anyhow!("Missing PTERO_APIKEY environment variable"))?;

    let mut headers = HeaderMap::new();
    headers.insert(CONTENT_TYPE, HeaderValue::from_static("application/json"));
    headers.insert(USER_AGENT, HeaderValue::from_static("osmiumd"));
    headers.insert(
        "Authorization",
        HeaderValue::from_str(&format!("Bearer {}", apikey))
            .map_err(|e| anyhow!("Invalid Authorization header format: {}", e))?,
    );

    // Send GET request to the API
    let response = client.get(&url).headers(headers).send().await?;

    if !response.status().is_success() {
        return Err(anyhow!(
            "Failed to fetch server resources: {:?}",
            response.status()
        ));
    }

    // Parse JSON response to `types::ResourcesResponse`
    let res = response.json::<types::ResourcesResponse>().await?;
    Ok(res.attributes.resources)
}

async fn fetch_plan_players(server_name: &String) -> Result<u32, anyhow::Error> {
    let client = reqwest::Client::new();
    let url = format!(
        "http://192.168.120.104:33224/v1/serverOverview?server={}",
        server_name
    );

    let mut headers = HeaderMap::new();
    headers.insert(CONTENT_TYPE, HeaderValue::from_static("application/json"));
    headers.insert(USER_AGENT, HeaderValue::from_static("osmiumd"));

    let response = client.get(&url).headers(headers).send().await?;

    if !response.status().is_success() {
        return Err(anyhow!(
            "Failed to fetch PLAN players: {:?}",
            response.status()
        ));
    }

    let json: Value = response.json().await?;

    let online_players = json
        .get("numbers")
        .and_then(|numbers| numbers.get("online_players"))
        .and_then(|online| online.as_str())
        .ok_or_else(|| anyhow!("Missing or invalid `numbers.online_players` field"))?;

    let online_players = online_players
        .parse::<u32>()
        .map_err(|_| anyhow!("Invalid `numbers.online_players` value: {}", online_players))?;

    Ok(online_players)
}
