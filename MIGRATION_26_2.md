# HibiscusCommons: Pinac 26.2 migration

## Review before implementation (2026-09-08)

Repository C:/Users/CHESV/Documents/GitHub/Masivo/Cosmetics/HibiscusCommons; clean initial master 8523cbe118bf70a56502ee7c8fc3b097aeb6667f tracking origin/master. Verified origin https://github.com/MasivoSMP/HibiscusCommons.git and remote default master; upstream https://github.com/HibiscusMC/HibiscusCommons.git is read-only. `git fetch origin`, `git pull --ff-only origin master` succeeded (already current); created dev-26.2 with `git switch -c dev-26.2`. No existing migration branch or unrelated changes. No applicable AGENTS.md found in project/parents. Upstream master observed ae2b031fc714b1b640fd5c3825ddc4182f6b7450; no upstream merge is proposed.

Target: accepted Pinac implementation 36efa7fe8c630737216393ca92cf14f1db2c759b, Canvas ddc374bb25e0f1e3cb6836aff89b538cbc10a919, Java 25. Read shared migration reference/progress and C:/Users/CHESV/Documents/GitHub/Pinac-26.2/MIGRATION_26_2.md. Original Masivo/Pinac is not consumed.

Applicable findings:
- onStart -> NMSHandlers.setup -> MinecraftVersion and VERSION_MAP -> reflective NMSUtils/NMSPackets/NMSPacketSender: 26.2 is absent, so startup rejects it. Existing 26.1 adapter includes channel intercepts, inventory/equipment/passengers/scale, entity wrappers, item colors and advancement toast internals; it must compile against actual 26.2, not be blindly selected.
- Preserve older adapters and public packet interfaces. Add v26_2_R1 following established adapter layout and explicit 26.2 routing. Keep common Java 21 bytecode where possible, compile common against target API to catch Adventure 5 removals; new internals adapter uses Java 25.
- Gradle 9.4.1 is already suitable. Use Weaver userdev 2.4.5 for the new adapter and io.canvasmc.pinac:dev-bundle:26.2-local with module metadata; common consumes io.canvasmc.pinac:pinac-api:26.2-local. No second Paper API on the target adapter. Existing HMCC_INCLUDE_HASH suppression is retained.
- Public references: [Paper 26.2 changes](https://papermc.io/news/26-2/) identify Adventure 5 and version-format changes; [Canvas userdev setup](https://docs.canvasmc.io/canvas/developers/plugins/userdev/) documents bundle consumption and avoiding duplicate APIs. Exact signature evidence will come from pinned target compilation/source inspection.
- ItemSerializer, ItemBuilder, Adventure helpers and optional hooks require target compilation and old-item/client validation. No raw vanilla player/world file migration, bed PDC migration, cube event handling or generated resource-pack change identified. Plugin-owned configuration/data keys and scheduling/packet contracts remain unchanged.

Plan: add and port only the 26.2 adapter/routing and build dependencies; retain all legacy support. Run target/full packaging checks, focused no-framework version regression, inspect archive/classes/resolution and record hashes. Commit coherent checkpoints and explicitly push only HEAD:refs/heads/dev-26.2. No Maven-local publication without orchestrator coordinate/lock approval. HMCCosmetics is a separate read-only consumer; it must later build against this provider.

Runtime gates (not run): isolated Pinac startup, channel injection/reflection, inventory cursor/state and equipment/passenger/scale packets, NPC/team/metadata/client rendering, toast advancement lifecycle, item round-trip using copied 1.21.11 data, optional hooks present/absent and Folia ownership/concurrency. Build success alone is not deployment certification.
