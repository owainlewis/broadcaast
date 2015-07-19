package models

/**
 * Represents an action on a comment such as like or dislike etc
 *
 * @param comment The comment ID
 * @param user The user ID
 * @param action The action (e.g. like, dislike, favorite etc)
 */
case class CommentAction(comment: Long, user: Long, action: String)

