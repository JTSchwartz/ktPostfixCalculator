//****************************************************************************
//
//      filename:  main.kt
//
//   description:  Builds a gui and implements a Postfix Calculator
//
//        author:  Jacob Schwartz
//      login id:  schwartzj1
//
//         class:  CPS 452
//    instructor:  Perugini
//    assignment:  Homework #1
//
//      assigned:  August 21, 2019
//           due:  August 28, 2019
//
//****************************************************************************

import javafx.beans.property.SimpleDoubleProperty
import javafx.beans.property.SimpleStringProperty
import javafx.event.EventHandler
import javafx.geometry.Orientation
import javafx.scene.input.KeyCode
import javafx.geometry.Pos
import javafx.scene.control.TableView
import javafx.scene.image.Image
import tornadofx.*
import java.util.*

val icon = Image("icon.png")

class CalcApp: App(icon, Gui::class)

class Gui: View("Postfix Calculator") {
	private val input = SimpleStringProperty()
	private var output = SimpleDoubleProperty()
	private val history = observableListOf<EqSol>()

	override val root = form {
		vbox {
			fieldset {
				field("Input") {
					textfield(input) {
						onKeyReleased = EventHandler {key ->
							if(key.code == KeyCode.ENTER) {
								onClick()
							}
						}
					}
				}
			}

			hbox {
				button("Calculate") {
					action {
						onClick()
					}
				}

				label("Value: ") {
					style {
						paddingLeft = 20
					}
				}

				label() {
					bind(output)
				}

				style {
					alignment = Pos.CENTER_LEFT
					paddingBottom = 20
				}
			}



			tableview<EqSol> {
				items = history
				column("Equation", EqSol::eq)
				column("Solution", EqSol::sol)

				columnResizePolicy = TableView.CONSTRAINED_RESIZE_POLICY

			}
		}
	}

	private fun onClick() {
		output.value = postfixCalc(input.value)
		history.add(EqSol(input.value, output.value))
		input.value = ""
	}

	private fun postfixCalc(eq: String?): Double {
		try {
			if(eq == null) {
				return 0.0
			}

			val eqSplit = eq.split(" ")
			val stack = Stack<Double>()

			for(expr in eqSplit) {
				if(expr.isDouble()) {
					stack.push(expr.toDouble())
				} else {
					when(expr) {
						"+"  -> stack.push(stack.pop() + stack.pop())
						"-"  -> {
							val x = stack.pop()
							val y = stack.pop()
							stack.push(y - x)
						}
						"*"  -> stack.push(stack.pop() * stack.pop())
						"/"  -> {
							val x = stack.pop()
							val y = stack.pop()
							stack.push(y / x)
						}
						else -> return 0.0
					}
				}
			}

			return stack.pop()
		} catch(err: EmptyStackException) {
			return 0.0
		}
	}

	init {
		with(root) {
			prefWidth = 350.0
		}
	}
}

class EqSol (var eq:String, var sol:Double)