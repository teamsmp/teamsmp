<script>
    import MainBlock from "$lib/components/MainBlock.svelte";
    import TitleBlock from "$lib/components/TitleBlock.svelte";
    import Para from "$lib/components/Para.svelte";
    import Sect from "$lib/components/Sect.svelte";
    import Heading from "$lib/components/Heading.svelte";
    import { toast } from "svelte-sonner";
    import { Root } from "$lib/components/ui/button";
    import * as Tabs from "$lib/components/ui/tabs";
    import { List } from "lucide-svelte";
    import { Content, Description, Trigger } from "$lib/components/ui/sheet";
    import * as Alert from "$lib/components/ui/alert";
    let serverIp = "teamsmp.spdns.eu";
    let javaPort = "33221";

    /**
     * @param {any} java
     */
    function copyIpToClipboard(java) {
        const address = java ? `${serverIp}:${javaPort}` : serverIp;

        navigator.clipboard
            .writeText(address)
            .then(() => {
                console.log(
                    `Copied server ${java ? "IP" : "address"} to clipboard: ${address}`
                );
                toast.success(
                    `Copied server ${java ? "IP" : "address"} to clipboard!`
                );
            })
            .catch((error) => {
                console.error(
                    `Failed to copy server ${java ? "IP" : "address"}: `,
                    error
                );
                toast.error(
                    `Error copying server ${java ? "IP" : "address"} to clipboard`,
                    {
                        description: "Check console for more details",
                    }
                );
            });
    }
</script>

<svelte:head>
    <title>Join &bull; Team SMP</title>
</svelte:head>

<MainBlock>
    <TitleBlock title="Join" subtitle="TEAM SMP" />

    <div id="maintext">
        <Para lead
            >Want to join the Team SMP? Follow these instructions and you'll be
            on in no time!</Para
        >

        <Tabs.Root value="java">
            <Tabs.List class="mb-4">
                <Tabs.Trigger value="java">Java Edition</Tabs.Trigger>
                <Tabs.Trigger value="console">Bedrock on Consoles</Tabs.Trigger>
                <Tabs.Trigger value="bedrock"
                    >Other Bedrock Edition</Tabs.Trigger
                >
            </Tabs.List>
            <Tabs.Content value="java">
                <Sect sectid="java-step1">
                    <Heading htype="macro">1. Add the server IP</Heading>
                    <Para>
                        Add the following IP to your server list in Minecraft by
                        clicking "add server" and typing it into the "server
                        address" box.
                    </Para>
                    <div class="mb-2 flex flex-row gap-1">
                        <code
                            class="rounded-s-md bg-zinc-200 px-3 py-2 dark:bg-zinc-800"
                            >{serverIp}:{javaPort}</code
                        >
                        <button
                            class="rounded-e-md bg-zinc-200 px-3 py-2 text-zinc-700 hover:bg-zinc-300 dark:bg-zinc-800 dark:text-zinc-400 hover:dark:bg-zinc-700"
                            on:click={() => copyIpToClipboard(true)}
                            >Copy</button
                        >
                    </div>
                </Sect>

                <Sect sectid="java-step2">
                    <Heading htype="macro">2. Join the server</Heading>
                    <Para>
                        Now you actually have to join the server. Press "done"
                        and double-click on the new entry in the server list.
                        Once joining, you will be asked to download a resource
                        pack automagically. Simply accept the request and wait
                        for it to complete the download.
                    </Para>
                </Sect>

                <Sect sectid="java-step3">
                    <Heading htype="macro">3. After joining</Heading>
                    <Para>
                        Now that you're in the server, you will spawn in the
                        Lobby <a
                            class="underline hover:text-sky-500"
                            href="https://best-friends.gitbook.io/team-smp/the-lobby"
                            ><i>(wiki)</i></a
                        >. The Guide NPC will tell you everything you need to
                        know to get into the survival world, but in short,
                        either walk forwards to the big portal or right click on
                        the Game Menu NPC to your left.
                    </Para>
                </Sect>
            </Tabs.Content>
            <Tabs.Content value="console">
                <Sect sectid="console-disclaimer">
                    <Alert.Root>
                        <Alert.Title>Disclaimer</Alert.Title>
                        <Alert.Description>
                            Your experience on the Team SMP will be a lot better
                            if you use Java edition to play, as it was
                            originally designed as a Java edition server.
                        </Alert.Description>
                    </Alert.Root>
                </Sect>

                <Sect sectid="console-step1">
                    <Heading htype="macro">1. Friend the account</Heading>
                    <Para>
                        To join the Team SMP on any console, you must go to the
                        friends list <b>in Minecraft</b> and add the account JunglTemple.
                        This will not work if you friend the account through your
                        console's built-in friend system.
                    </Para>
                </Sect>

                <Sect sectid="console-step2">
                    <Heading htype="macro">2. Join the friend</Heading>
                    <Para>
                        The only thing to do now is to join the new friend, and
                        there you go!
                    </Para>
                </Sect>

                <Sect sectid="console-step3">
                    <Heading htype="macro">3. After joining</Heading>
                    <Para>
                        Now that you're in the server, you will spawn in the
                        Lobby <a
                            class="underline hover:text-sky-500"
                            href="https://best-friends.gitbook.io/team-smp/the-lobby"
                            ><i>(wiki)</i></a
                        >. The Guide NPC will tell you everything you need to
                        know to get into the survival world, but in short,
                        either walk forwards to the big portal or right click on
                        the Game Menu NPC to your left.
                    </Para>
                </Sect>
            </Tabs.Content>
            <Tabs.Content value="bedrock">
                <Sect sectid="bedrock-disclaimer">
                    <Alert.Root>
                        <Alert.Title>Disclaimer</Alert.Title>
                        <Alert.Description>
                            Your experience on the Team SMP will be a lot better
                            if you use Java edition to play, as it was
                            originally designed as a Java edition server.
                        </Alert.Description>
                    </Alert.Root>
                </Sect>

                <Sect sectid="bedrock-step1">
                    <Heading htype="macro">1. Add the server</Heading>
                    <Para>
                        Go to the Servers section of the Play menu. Then, scroll
                        to the bottom and click "Add Server". Type in the
                        following server address and leave the port as default:
                    </Para>
                    <div class="mb-2 flex flex-row gap-1">
                        <code
                            class="rounded-s-md bg-zinc-200 px-3 py-2 dark:bg-zinc-800"
                            >{serverIp}</code
                        >
                        <button
                            class="rounded-e-md bg-zinc-200 px-3 py-2 text-zinc-700 hover:bg-zinc-300 dark:bg-zinc-800 dark:text-zinc-400 hover:dark:bg-zinc-700"
                            on:click={() => copyIpToClipboard(false)}
                            >Copy</button
                        >
                    </div>
                </Sect>

                <Sect sectid="bedrock-step2">
                    <Heading htype="macro">2. Join the server</Heading>
                    <Para>
                        Click "Save", then click on the server in the menu and
                        click "Play" on the right of the screen.
                    </Para>
                </Sect>

                <Sect sectid="bedrock-step3">
                    <Heading htype="macro">3. After joining</Heading>
                    <Para>
                        Now that you're in the server, you will spawn in the
                        Lobby <a
                            class="underline hover:text-sky-500"
                            href="https://best-friends.gitbook.io/team-smp/the-lobby"
                            ><i>(wiki)</i></a
                        >. The Guide NPC will tell you everything you need to
                        know to get into the survival world, but in short,
                        either walk forwards to the big portal or right click on
                        the Game Menu NPC to your left.
                    </Para>
                </Sect>
            </Tabs.Content>
        </Tabs.Root>
        <Para>We hope you have fun playing on the Team SMP!</Para>
    </div>
</MainBlock>
