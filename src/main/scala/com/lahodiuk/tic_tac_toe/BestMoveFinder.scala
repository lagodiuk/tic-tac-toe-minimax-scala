package com.lahodiuk.tic_tac_toe

trait BestMoveFinder[S <: State[S]] {
  // produces the state, which represents
  // the most optimal move for the given
  // state of the game
	def move(s: S): S
}
