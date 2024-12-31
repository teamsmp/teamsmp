# Team SMP Monorepo

This is now where the Team SMP's code is stored: an amazing monorepo! I regret making it a monorepo, but, well, no going back now!

## Projects

This contains an assortment of different projects related to the Team SMP. Here they are described:

### `aetherium`

`aetherium` was the Team SMP's custom plugin, but since the transition to a Velocity network, it has been phased out in favour of other plugins like [`neodymium`](#neodymium).

### `cerium`

`cerium` was an experimental Velocity plugin, although it has been discontinued in favour of `osmium` and [`osmiumd`](#osmiumd).

### `elysium`

`elysium` is the Team SMP's [official website](https://teamsmp.pages.dev/) coded in [Svelte](https://svelte.dev/) with [SvelteKit](https://kit.svelte.dev/) hosted through Cloudflare Pages at [teamsmp.pages.dev](https://teamsmp.pages.dev/).

### `neodymium`

`neodymium` is the Team SMP's brand new Season 3 Survival plugin, the features of which have not yet been implemented, but will include Survival-focused additions like the Recognition Rewards system and Grind Mode. Stay tuned for a blog post explaining the two.

### `osmiumd`

`osmiumd` is the orchestration server for the Team SMP network, coded in [Rust](https://www.rust-lang.org/) using [Axum](https://docs.rs/axum/) and will soon handle server marks (status) and staggered restarting, along with `osmium`, the Minecraft implementation of this.
