package com.askattest.http.identifiers.pathtohost

import io.buoyant.linkerd.IdentifierInitializer


class PathToHostIdentifierInitializer extends IdentifierInitializer {
  override def configId: String = "com.askattest.pathToHost"

  override def configClass: Class[_] = return classOf[PathToHostIdentifierConfig]
}
