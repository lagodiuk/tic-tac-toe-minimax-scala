package com.lahodiuk.tic_tac_toe

trait State[S <: State[S]] {
  def isGameOver      : Boolean
  def playerOneWin    : Boolean
  def playerTwoWin    : Boolean
  def isPlayerOneTurn : Boolean
  // generates all possible states
  // which can be produced from
  // the current state of the game
  def generateStates  : Seq[S]
}
