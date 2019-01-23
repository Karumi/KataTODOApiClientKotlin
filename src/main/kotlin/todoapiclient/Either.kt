package todoapiclient

sealed class Either<out L, out R> {

    companion object {
        fun <L> left(left: L): Either<L, Nothing> = Either.Left(left)
        fun <R> right(right: R): Either<Nothing, R> = Either.Right(right)
    }

    val left: L?
        get() = when (this) {
            is Left -> l
            is Right -> null
        }

    val right: R?
        get() = when (this) {
            is Left -> null
            is Right -> r
        }

    data class Left<out L>(val l: L) : Either<L, Nothing>()
    data class Right<out R>(val r: R) : Either<Nothing, R>()
}