package com.askattest.http.identifiers.pathtohost

import com.fasterxml.jackson.annotation.JsonIgnore
import com.twitter.finagle.http.Request
import com.twitter.finagle.{Dtab, Path}
import io.buoyant.linkerd.protocol.HttpIdentifierConfig
import io.buoyant.router.RoutingFactory.Identifier


class PathToHostIdentifierConfig extends HttpIdentifierConfig{

  @JsonIgnore
  override def newIdentifier(prefix: Path, baseDtab: () => Dtab): Identifier[Request] = {
    new PathToHostIdentifier(prefix, baseDtab)
  }
}
