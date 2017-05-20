package com.askattest.http.identifiers.pathtohost

import java.net.URI

import com.twitter.finagle.http.Request
import com.twitter.finagle.{Dtab, Path}
import com.twitter.util.Future
import io.buoyant.router.RoutingFactory
import io.buoyant.router.RoutingFactory.{RequestIdentification, UnidentifiedRequest}


case class PathToHostIdentifier(
                                 prefix: Path,
                                 baseDtab: () => Dtab = () => Dtab.base
                               ) extends RoutingFactory.Identifier[Request] {

  private[this] val MoveOn =
    Future.value(new UnidentifiedRequest[Request]("MoveOn to next identifier"))

  def apply(req: Request): Future[RequestIdentification[Request]] = {
    // Example request URI: /www.google.com:80
    val host = new URI(s"http:/${req.uri}").getHost
    req.headerMap.set("Host", host)
    MoveOn
  }
}
