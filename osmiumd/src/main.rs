mod check;
mod routes;
mod storage;
mod types;

use axum::{routing::get, Router};
use tokio::time::{self, Duration};

#[tokio::main]
async fn main() {
    let app = Router::new()
        .route("/", get(routes::index))
        .route("/servers", get(routes::get_all_servers))
        .route("/servers/:name", get(routes::get_server))
        .route(
            "/servers/:name/mark",
            get(routes::get_server_mark).post(routes::set_server_mark),
        );

    tokio::spawn(async {
        let mut interval = time::interval(Duration::from_secs(60)); // 60 seconds = 1 minute
        loop {
            interval.tick().await; // Wait for the next tick (every minute)
            check::periodic_check().await; // Call your periodic check function
        }
    });
    
    let listener = tokio::net::TcpListener::bind("0.0.0.0:33200")
        .await
        .unwrap();
    axum::serve(listener, app).await.unwrap();
}
