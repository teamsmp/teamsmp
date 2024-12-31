use crate::types;
use std::fs;

pub(crate) fn load_servers() -> types::Servers {
    let data = fs::read_to_string("servers.json").expect("Failed to read servers.json");
    serde_json::from_str(&data).expect("Failed to parse servers.json")
}

pub(crate) fn save_servers(servers: &types::Servers) {
    let data = serde_json::to_string_pretty(servers).expect("Failed to serialize servers");
    fs::write("servers.json", data).expect("Failed to write to servers.json");
}
