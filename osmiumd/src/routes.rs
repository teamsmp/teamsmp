use crate::{storage, types};
use axum::extract::{Json, Path};
use std::collections::HashMap;

pub(crate) async fn index() -> Json<HashMap<&'static str, &'static str>> {
    let mut res = HashMap::new();
    res.insert(
        "welcome",
        "Welcome to osmiumd! Check out another route to get started.",
    );
    Json(res)
}

pub(crate) async fn get_server(Path(name): Path<String>) -> Json<types::ServerResponse> {
    let servers = storage::load_servers();
    let server = match servers.servers.get(&name).cloned() { 
        Some(server) => server,
        None => { return Json(types::ServerResponse{
            server: name,
            details: None,
            error: Some("server not found".to_string())
        }) },
    };
    Json(types::ServerResponse {
        server: name,
        details: Some(server),
        error: None
    })
}

pub(crate) async fn get_all_servers() -> Json<types::Servers> {
    let servers = storage::load_servers();
    Json(servers)
}

pub(crate) async fn get_server_mark(
    Path(name): Path<String>,
) -> Json<types::ServerMarkResponse> {
    let servers = storage::load_servers();
    let server = match servers.servers.get(&name).cloned() {
        Some(server) => server,
        None => {
            return Json(types::ServerMarkResponse {
                server: name,
                mark: None,
                error: Some("server not found".to_string()),
            })
        }
    };

    let server_mark = server.mark;
    let server_name = server.name;

    let res = types::ServerMarkResponse {
        server: server_name,
        mark: Some(server_mark),
        error: None,
    };

    Json(res)
}

pub(crate) async fn set_server_mark(Path(name): Path<String>, Json(payload): Json<types::SetServerMarkRequest>) -> Json<types::ServerMarkResponse> {
    let mut servers = storage::load_servers();
    
    // servers.servers.get_mut(&name).unwrap().mark = payload.clone().mark;
    match servers.servers.get_mut(&name) {
        Some(server) => server,
        None => {
            return Json(types::ServerMarkResponse {
                server: name,
                mark: None,
                error: Some("server not found".to_string()),
            })   
        }
    }.mark = payload.clone().mark;
    
    storage::save_servers(&servers);
    
    let res = types::ServerMarkResponse {
        server: name,
        mark: Some(payload.mark),
        error: None,
    };
    
    Json(res)
}