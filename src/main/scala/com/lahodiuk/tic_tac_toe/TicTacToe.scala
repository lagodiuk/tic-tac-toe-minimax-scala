package com.lahodiuk.tic_tac_toe

class TicTacToe(playerOne: BestMoveFinder[TicTacToeState], 
                playerTwo: BestMoveFinder[TicTacToeState]) {
  
  private var players = List(playerOne, playerTwo)

  private var game: TicTacToeState = 
    new TicTacToeState(DIMENSION, DIMENSION)
  
  def play = {
    while(!game.isGameOver) {
      val player = players.head
      game = player.move(game)
      println(display(game))
      players = players.tail :+ player
    }
    if(game.playerOneWin) println("Player One win!")
    else if(game.playerTwoWin) println("Player Two win!")
    else println("Draw!")
  }
    
  def display(game: TicTacToeState) = 
    (1 to DIMENSION).map(row =>
      (1 to DIMENSION).map(col => {
        val p = Position(row, col)
        if(game.playerOnePositions contains p) X_PLAYER 
        else if(game.playerTwoPositions contains p) O_PLAYER 
        else EMPTY_CELL
      }).mkString).mkString("\n") + "\n"
    
  final val DIMENSION  = 3
  final val X_PLAYER   = "X"
  final val O_PLAYER   = "O"
  final val EMPTY_CELL = "."
}
