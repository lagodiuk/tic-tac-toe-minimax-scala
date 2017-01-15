/**
 * Copyright 2017 Yurii Lahodiuk (yura.lagodiuk@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.lahodiuk.tic_tac_toe

class MinMaxStrategyFinder[S <: State[S]] extends BestMoveFinder[S] {

  // Here, and in other methods
  // parameter s - is a current state of the game
  def move(s: S): S = 
    if(s.isPlayerOneTurn) firstTurn(s).state
    else secondTurn(s).state
  
  def secondTurn(s: S): Outcome = 
    bestMoveFinder(minimize, firstTurn, PLAYER_ONE_WIN, s)
  
  def firstTurn(s: S): Outcome = 
    bestMoveFinder(maximize, secondTurn, PLAYER_ONE_LOOSE, s)
  
  def bestMoveFinder(strategy: Criteria, opponentMove: S => Outcome, 
                     worstOutcome: Int, s: S): Outcome = 
    if(s.isGameOver) Outcome(outcomeOfGame(s), s)
    else s.generateStates
          .foldLeft(Outcome(worstOutcome, s)){(acc, state) => {
              val outcome = opponentMove(state).cost
              if(strategy(outcome, acc.cost)) Outcome(outcome, state)
              else acc}}
  
  def outcomeOfGame(s: S): Int = 
    if (s.playerOneWin) PLAYER_ONE_WIN 
    else if(s.playerTwoWin) PLAYER_ONE_LOOSE 
    else DRAW
  
  case class Outcome(cost: Int, state: S)
  
  val PLAYER_ONE_WIN   =  1 // the outcome, when first player wins
  val PLAYER_ONE_LOOSE = -1 // the outcome, when second player wins
  val DRAW             =  0 // the outcome, when nobody wins
  
  type Criteria = (Int, Int) => Boolean
  def maximize: Criteria = (a, b) => a >= b
  def minimize: Criteria = (a, b) => a <= b
}
