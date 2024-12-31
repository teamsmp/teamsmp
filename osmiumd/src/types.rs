use serde::{Deserialize, Serialize};
use std::collections::HashMap;

#[derive(Serialize, Deserialize, Debug, Clone)]
pub(crate) struct ServerResources {
    pub(crate) memory_bytes: u64,
    pub(crate) cpu_absolute: f64,
    pub(crate) disk_bytes: u64,
    pub(crate) network_rx_bytes: u64,
    pub(crate) network_tx_bytes: u64,
    pub(crate) uptime: u64,
}

#[derive(Serialize, Deserialize, Debug, Clone)]
pub(crate) struct Server {
    pub(crate) name: String,
    pub(crate) mark: String,
    pub(crate) players: u32,
    pub(crate) id: String,
    pub(crate) resources: ServerResources,
}

#[derive(Serialize, Deserialize, Debug, Clone)]
pub(crate) struct ServerResponse {
    pub(crate) server: String,
    pub(crate) details: Option<Server>,
    pub(crate) error: Option<String>,
}

#[derive(Serialize, Deserialize, Debug, Clone)]
pub(crate) struct Servers {
    pub(crate) servers: HashMap<String, Server>,
}

#[derive(Serialize, Deserialize, Debug, Clone)]
pub(crate) struct ServerMarkResponse {
    pub(crate) server: String,
    pub(crate) mark: Option<String>,
    pub(crate) error: Option<String>,
}

#[derive(Serialize, Deserialize, Debug, Clone)]
pub(crate) struct SetServerMarkRequest {
    pub(crate) mark: String,
}

#[derive(Serialize, Deserialize, Debug, Clone)]
pub(crate) struct ResourcesResponse {
    pub(crate) object: String,
    pub(crate) attributes: ResourcesAttributes,
}

#[derive(Serialize, Deserialize, Debug, Clone)]
pub(crate) struct ResourcesAttributes {
    pub(crate) current_state: String,
    pub(crate) is_suspended: bool,
    pub(crate) resources: ServerResources,
}