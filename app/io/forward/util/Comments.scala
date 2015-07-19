package io.forward.util

import models._

object Comments {

  def extractAvatars(stream: Channel, comments: Seq[Item]): Set[String] = {
    val commentParticipants: Seq[String] = comments flatMap (_.userImage)
    val streamCreator: Option[Seq[String]] = stream.createdBy map (user => Seq(User.imageURL(user.email)))
    (commentParticipants ++ streamCreator.getOrElse(Nil)).toSet
  }
}
