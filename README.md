# MTR-OTP
MTR-OTP is a mod that attempts to fixes/patch some known issues for the last version of [Minecraft Transit Railway 3.0](https://github.com/Minecraft-Transit-Railway/Minecraft-Transit-Railway) to improve the playing experience.  

This is a temporary solution to provide a better playing experiences before the next version of MTR is released.  
Currently supports 1.19.2 - 1.19.4

## Features
- Attempt to use Frustum Culling to increase frame rates by skipping train rendering, may cause train flickering. (Client)
- Attempt to increase frame rates for the Dashboard Widget Map. (Client)
- A faster **Bridge Creator** by blacklisting more appropriate position (Server)
- Use elapsed time to ensure server train runs at a consistent rate regardless of TPS.

## Config
The client config file are automatically generated in `config/mtrotp.json` once the client starts up.

| Config Key          | Default Value | Description                                                                                        | Effect   |
|---------------------|---------------|----------------------------------------------------------------------------------------------------|----------|
| cullTrain           | true          | Use frustum culling to skip rendering train cars that are outside of camera bounds to improve FPS. | Varies   |
| dashboardLazyRender | true          | Optimize the dashboard world map to improve FPS.                                                   | Varies   |

The server config file are automatically generated in `config/mtrotp_server.json` once the server starts up.

| Config Key          | Default Value | Description                                                                                   | Effect |
|---------------------|---------------|-----------------------------------------------------------------------------------------------|--------|
| trainUpdateDistance | 128           | This removes the hardcoded 128 blocks radius train update in MTR, allowing for customization. | N/A    |

## Bugs/Suggestions
You may open an GitHub issue [here](https://github.com/DistrictOfJoban/MTR-OTP/issues)

## License
This project is licensed under the MIT License.