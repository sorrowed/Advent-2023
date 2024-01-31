package advent

import advent.day20.*

import kotlin.test.Test
import kotlin.test.assertEquals

class Day20 {

    private fun createModules(): List<Module> {
        val button = ButtonModule("button")
        val broadcaster = BroadcastModule("broadcaster")
        val a = FlipFlopModule("a")
        val b = FlipFlopModule("b")
        val c = FlipFlopModule("c")
        val inv = ConjunctionModule("inv")

        button.connect(broadcaster)
        broadcaster.connect(a, b, c)
        a.connect(b)
        b.connect(c)
        c.connect(inv)
        inv.connect(a)

        return listOf(button, broadcaster, a, b, c, inv)
    }

    @Test
    fun `given demo modules when processed then result is correct`() {

        val modules = createModules()
        modules.press(1)

        Module.trace.forEach { println(it) }

        assertEquals(
                listOf("button -low-> broadcaster",
                        "broadcaster -low-> a",
                        "broadcaster -low-> b",
                        "broadcaster -low-> c",
                        "a -high-> b",
                        "b -high-> c",
                        "c -high-> inv",
                        "inv -low-> a",
                        "a -low-> b",
                        "b -low-> c",
                        "c -low-> inv",
                        "inv -high-> a"),
                Module.trace
        )

        assertEquals(true, modules.filterIsInstance<FlipFlopModule>().all { it.state == FlipFlopModule.State.OFF })
    }

    @Test
    fun `given demo modules when pressed 1000 times then number of pulses is correct`() {

        val modules = createModules()

        modules.press(1000)

        assertEquals(8000, Module.lowPulses)
        assertEquals(4000, Module.highPulses)
        assertEquals(32000000, Module.lowPulses * Module.highPulses)
    }

    @Test
    fun `given demo input when parsed and repeated 3 times then output is correct`() {
        val input = listOf(
                "broadcaster -> a",
                "%a -> inv, con",
                "&inv -> b",
                "%b -> con",
                "&con -> output")

        val modules = Module.parseComplete(input)

        modules.press(4)

        assertEquals(
                listOf(
                        "button -low-> broadcaster",
                        "broadcaster -low-> a",
                        "a -high-> inv",
                        "a -high-> con",
                        "inv -low-> b",
                        "con -high-> output",
                        "b -high-> con",
                        "con -low-> output",
                        "button -low-> broadcaster",
                        "broadcaster -low-> a",
                        "a -low-> inv",
                        "a -low-> con",
                        "inv -high-> b",
                        "con -high-> output",
                        "button -low-> broadcaster",
                        "broadcaster -low-> a",
                        "a -high-> inv",
                        "a -high-> con",
                        "inv -low-> b",
                        "con -low-> output",
                        "b -low-> con",
                        "con -high-> output",
                        "button -low-> broadcaster",
                        "broadcaster -low-> a",
                        "a -low-> inv",
                        "a -low-> con",
                        "inv -high-> b",
                        "con -high-> output",
                ),
                Module.trace)
    }

    @Test
    fun `given demo input when parsed and repeated 1000 times then pulses is correct`() {
        val input = listOf(
                "broadcaster -> a",
                "%a -> inv, con",
                "&inv -> b",
                "%b -> con",
                "&con -> output")

        val modules = Module.parseComplete(input)

        modules.press(1000)

        assertEquals(4250, Module.lowPulses)
        assertEquals(2750, Module.highPulses)
        assertEquals(11687500, Module.lowPulses * Module.highPulses)
    }

}