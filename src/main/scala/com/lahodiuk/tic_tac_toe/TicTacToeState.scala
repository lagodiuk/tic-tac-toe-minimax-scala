package com.lahodiuk.tic_tac_toe

case class Position(val row: Int, val col: Int)

class TicTacToeState(val playerOnePositions : Set[Position],
                     val playerTwoPositions : Set[Position],
                     val availablePositions : Set[Position],
                     val isPlayerOneTurn : Boolean,
                     val winLength : Int) extends State[TicTacToeState] {
  
  lazy val isGameOver  : Boolean = 
    availablePositions.isEmpty || playerOneWin || playerTwoWin
    
  lazy val playerOneWin: Boolean = checkWin(playerOnePositions)
  
  lazy val playerTwoWin: Boolean = checkWin(playerTwoPositions)
  
  def generateStates: Seq[TicTacToeState] = 
    for(pos <- availablePositions.toSeq) yield makeMove(pos)
            
  def makeMove(p: Position): TicTacToeState = {
    assert(availablePositions.contains(p))
    if(isPlayerOneTurn) 
      new TicTacToeState(
          playerOnePositions + p, 
          playerTwoPositions, 
          availablePositions - p, 
          !isPlayerOneTurn, 
          winLength)
    else new TicTacToeState(
          playerOnePositions, 
          playerTwoPositions + p, 
          availablePositions - p, 
          !isPlayerOneTurn, 
          winLength)}
      
  def checkWin(positions: Set[Position]): Boolean =
    directions.exists(winConditionsSatisified(_)(positions))
    
  final val directions = List(leftDiagonal, rightDiagonal, row, column)
  
  type StepOffsetGen = (Position, Int) => Position
  
  def leftDiagonal : StepOffsetGen = 
    (pos, offset) => Position(pos.row + offset, pos.col + offset)
    
  def rightDiagonal : StepOffsetGen = 
    (pos, offset) => Position(pos.row - offset, pos.col + offset)
    
  def row : StepOffsetGen = 
    (pos, offset) => Position(pos.row + offset, pos.col)
    
  private def column : StepOffsetGen = 
    (pos, offset) => Position(pos.row, pos.col + offset)
    
  def winConditionsSatisified(step: StepOffsetGen)
                             (positions: Set[Position]): Boolean =
    positions exists( position =>
      (0 until winLength) forall( offset =>
        positions contains step(position, offset)))
    
  // Just additional constructor for convenience  
  def this(dimension: Int, winLength: Int) = this(
      Set(), Set(), // positions of the players are empty initially
      (for{row <- (1 to dimension) // initialize available positions 
           col <- (1 to dimension)} yield Position(row, col)).toSet, 
      true, winLength)
}
