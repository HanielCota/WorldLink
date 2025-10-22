# WorldLink

A Minecraft plugin that enables fast teleportation between worlds with customizable spawn points.

## 📋 About

**WorldLink** is a plugin developed for Minecraft servers that facilitates navigation between different worlds (Overworld, Nether, The End) through simple commands and custom spawn point configuration.

## ✨ Features

- **Inter-world teleportation**: Command for quick teleportation between Overworld, Nether and The End
- **Custom spawn**: Configure specific spawn points for each world
- **Configurable messages**: Fully customizable message system
- **Modern interface**: Intuitive commands with visual feedback
- **Optimized performance**: Asynchronous teleports for better performance

## 🎮 Commands

### `/tpworld <world>` or `/worldtp <world>`

Teleports the player to the specified world.

**Available worlds:**

- `overworld` - Main world
- `nether` - Nether world
- `the_end` - The End world

**Example:**

```
/tpworld nether
/worldtp the_end
```

### `/setworldspawn`

Sets the custom spawn point for the current world.

**Usage:**

1. Position yourself at the desired location
2. Execute `/setworldspawn`
3. The spawn will be saved with your exact coordinates (including rotation)

## ⚙️ Configuration

### `config.yml` File

```yaml
messages:
  teleporting: "§eTeleporting to world '{world}'..."
  success: "§aYou have been successfully teleported!"
  world-not-found: "§cError: World '{world}' was not found or is not loaded."
  location-not-safe: "§cError: The spawn point of world '{world}' is not safe!"
  generic-failure: "§cError: Teleportation failed due to an unknown reason."
  set-spawn: "§aSpawn for world '{world}' set to §f{coords}"

spawns:
  world: "0.0,64.0,0.0,0.0,0.0"
  world_nether: "0.0,64.0,0.0,0.0,0.0"
  world_the_end: "0.0,64.0,0.0,0.0,0.0"
```

### Message Customization

All messages can be customized in the `config.yml` file. Use placeholders for dynamic information:

- `{world}` - World name
- `{coords}` - Spawn coordinates

## 🛠️ Installation

### Requirements

- **Minecraft**: 1.21+
- **Server**: Paper/Spigot
- **Java**: 21+

### Installation Steps

1. **Download**: Download the plugin `.jar` file
2. **Installation**: Place the file in your server's `plugins/` folder
3. **Restart**: Restart the server
4. **Configuration**: Edit the `config.yml` file as needed

### Development

To compile the plugin from source code:

```bash
# Clone the repository
git clone https://github.com/hanielcota/WorldLink.git
cd WorldLink

# Compile the project
./gradlew build

# The .jar file will be in build/libs/
```

## 🏗️ Architecture

### Project Structure

```
src/main/java/com/github/hanielcota/
├── WorldLink.java                 # Main plugin class
├── commands/
│   ├── SetWorldSpawnCommand.java  # Command to set spawn
│   └── WorldTPCommand.java        # Teleport command
├── enums/
│   ├── TeleportResult.java        # Teleport results
│   └── TeleportTargetWorld.java   # Available worlds
├── services/
│   └── TeleportService.java       # Teleport service
└── utils/
    ├── ConfigUtils.java           # Configuration utility
    ├── MessageService.java        # Message service
    ├── MessageUtils.java          # Message utility
    └── SpawnConfigUtils.java      # Spawn utility
```

### Technologies Used

- **Java 21**: Programming language
- **Paper API**: Minecraft server API
- **Lombok**: Boilerplate reduction
- **ACF (Annotation Command Framework)**: Command framework
- **Gradle**: Build system

## 🔧 Development

### Technical Features

- **Asynchronous Teleports**: Uses `CompletableFuture` for non-blocking teleports
- **Robust Validation**: Safety and world existence verification
- **Message System**: Placeholder and formatting support
- **Flexible Configuration**: Configuration system with fallbacks
- **Detailed Logging**: Logging system for debugging

### Design Patterns

- **Separation of Responsibilities**: Each class has a specific function
- **Dependency Injection**: Services are injected via constructors
- **Null Safety**: Robust validations against null values
- **Early Returns**: Avoids unnecessary code nesting

## 📝 License

This project is under the MIT license. See the `LICENSE` file for more details.

## 🤝 Contributing

Contributions are welcome! To contribute:

1. Fork the project
2. Create a branch for your feature (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📞 Support

For support, bug reports or feature requests:

- **Issues**: [GitHub Issues](https://github.com/hanielcota/WorldLink/issues)
- **Discord**: [Discord Server](https://discord.gg/example)

## 📊 Statistics

- **Version**: 1.0-SNAPSHOT
- **API Version**: 1.21
- **Java**: 21+
- **Dependencies**: Paper API, Lombok, ACF

---

**Developed with ❤️ for the Minecraft community**
