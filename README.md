# linkerd-plugins
Useful linkerd plugins

## Path to Host identifier

The Path to Host identifier sets the `Host` header to the first segment in the path. This is useful when hitting linkerd directly (as opposed to using it as an HTTP proxy). If you want to use linkerd in this mode while hitting external services (e.g. 3rd part APIs), setting the `Host` this way is mandatory or the request will fail as the `Host` header will be set to your linkerd host+port and the server will likely respond with 404 Not Found.
 
The following config makes it so requests to `http://localhost:4140/www.google.co.uk/search?q=linkerd` go to `www.google.co.uk:443/search?q=linkerd` (443 is the default port). A port can be explicitly specified like so: `http://localhost:4140/www.google.co.uk:80/search?q=linkerd`. Both of these requests will set the `Host` header to `www.google.co.uk`.

```
routers:
- protocol: http
  label: external

  identifier:
  - kind: com.askattest.pathToHost
  - kind: io.l5d.path
    segments: 1
    consume: true

  dtab: |
    /ph => /$/io.buoyant.rinet;
    /svc => /ph/443;
    /svc => /$/io.buoyant.porthostPfx/ph;

  servers:
  - ip: 0.0.0.0
    port: 4140
    clearContext: true
  client:
    kind: io.l5d.static
    configs:
    - prefix: /$/io.buoyant.rinet/443/{hostname}
      tls:
        commonName: "{hostname}"
```

## Building

To build a plugin, run `./sbt ${pluginName}/assembly` from the root plugins directory. For example:

```
./sbt pathToHost/assembly
```

This will produce the plugin JAR at
`path-to-host/target/scala-2.12/path-to-host-assembly-0.1.0.jar`.

## Installing

To install a plugin with linkerd, simply move the plugin JAR into linkerd's
plugin directory (`$L5D_HOME/plugins`).
