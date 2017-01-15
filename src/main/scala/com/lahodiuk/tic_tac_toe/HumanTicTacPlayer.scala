package com.lahodiuk.tic_tac_toe

class HumanTicTacPlayer extends BestMoveFinder[TicTacToeState] {
  def move(s: TicTacToeState): TicTacToeState = {
    println("Input the row and the column, please:")
    val (row, col) = readf2("{0, number} {1,number}")
    val pos = Position(row.asInstanceOf[Long].toInt, col.asInstanceOf[Long].toInt)
    try {
      s.makeMove(pos)
    } catch {
      case _: Throwable => {
        println("Such move is impossible!")
        move(s)}}}
}
