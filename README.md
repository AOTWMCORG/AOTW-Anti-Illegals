# AOTW-Anti-Illegals

IllegalItemsPlugin

A lightweight Minecraft plugin for Spigot/Paper servers that automatically detects and removes illegal or overpowered items from players’ inventories.

Note: This plugin includes a configurable bypass system for certain players. You can specify which players should be allowed to bypass the checks in config.yml.

Features

Detects and removes items with:

Excessive enchantments

Overstacked amounts

(Optional) Other illegal modifications

Automatically scans:

Player inventories on join

Items picked up

Inventory clicks and held items

Configurable bypass for specific players in config.yml

Fully compatible with Spigot and Paper servers

Installation

Place IllegalItemsPlugin-1.0-SNAPSHOT-shaded.jar in your server’s plugins folder.

Start or restart your server.

Configure the plugin in plugins/IllegalItemsPlugin/config.yml to add any players that should bypass the checks.

Configuration (config.yml)

Example:

bypass-players:
  - YourName
  - TrustedAdmin


bypass-players: List of player names who will bypass the illegal item detection.

Other settings include max enchantment levels and max stack sizes.




This project is licensed under the MIT License – see the LICENSE
 file for details.
