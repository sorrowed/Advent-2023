package advent.day20

import advent.support.lcm
import java.io.File

enum class Pulse { LOW, HIGH }

enum class ModuleType {
    BUTTON, FLIFLOP, CONJUNCTION, BROADCAST, TEST
}

typealias PulseCallback = (Int, Module, Pulse) -> Unit

abstract class Module(val id: String, val type: ModuleType) {
    private val pulses = mutableListOf<Pulse>()

    val outputs = mutableListOf<Module>()

    fun connect(vararg outputs: Module) {
        this.outputs.addAll(outputs)
        outputs.forEach { output -> output.connected(this) }
    }

    open fun connected(source: Module) {}

    abstract fun `in`(source: String, type: Pulse)

    fun update(loop: Int, pulsed: PulseCallback? = null): Boolean {
        val processed = pulses.isNotEmpty()

        while (pulses.isNotEmpty()) {
            val type = pulses[0]
            pulses.removeAt(0)

            outputs.forEach {
                if (type == Pulse.LOW) {
                    ++lowPulses
                    print("$id -low-> ${it.id}")
                } else {
                    ++highPulses
                    print("$id -high-> ${it.id}")
                }
                it.`in`(id, type)

                if (pulsed != null) {
                    pulsed(loop, this, type)

                }
            }

        }
        return processed
    }

    protected fun pulse(type: Pulse) {
        pulses += type
    }

    companion object {
        var lowPulses = 0L
        var highPulses = 0L

        val trace = mutableListOf<String>()

        fun reset() {
            lowPulses = 0L
            highPulses = 0L
            trace.clear()
        }

        fun print(message: String) {
            trace += message
        }

        fun parse(input: String): Module {
            return if (input.startsWith("broadcaster")) {
                BroadcastModule(input)
            } else if (input.startsWith("output")) {
                TestModule(input)
            } else if (input.startsWith("%")) {
                FlipFlopModule(input.substring(1))
            } else if (input.startsWith("&")) {
                ConjunctionModule(input.substring(1))
            } else {
                error("Invalid module type")
            }
        }

        /**
         * This is fugly
         */
        fun parseComplete(input: Iterable<String>): List<Module> {

            val pairs = input.map { it.split(" -> ") }.map { it[0] to it[1].split(", ") }

            val modules = mutableListOf<Module>()
            modules.addAll(pairs.map { pair ->
                parse(pair.first)
            })

            for (module in modules) {

                val outputIds = pairs.find { it.first.endsWith(module.id) }!!.second

                outputIds.forEach { outputId ->
                    val outputModule = modules.firstOrNull { module -> module.id == outputId }
                    if (outputModule != null) {
                        module.connect(outputModule)
                    } else {
                        module.connect(TestModule("output"))
                    }
                }
            }

            // Add button to the front and connect to broadcaster
            modules.add(0, ButtonModule("button").also { it.outputs += modules.find { module -> module.id == "broadcaster" }!! })

            return modules
        }
    }
}

class ButtonModule(id: String, private val message: Boolean = false) : Module(id, ModuleType.BUTTON) {
    override fun `in`(source: String, type: Pulse) {
        if (type == Pulse.LOW) {
            pulse(Pulse.LOW)
        } else {
            error("Button can't do high pulse")
        }
    }
}

class FlipFlopModule(id: String) : Module(id, ModuleType.FLIFLOP) {

    enum class State { OFF, ON }

    var state = State.OFF
        private set

    override fun `in`(source: String, type: Pulse) {
        if (type != Pulse.LOW) return

        state = when (state) {
            State.OFF -> State.ON
            State.ON -> State.OFF
        }

        pulse(when (state) {
            State.OFF -> Pulse.LOW
            State.ON -> Pulse.HIGH
        })
    }
}

class ConjunctionModule(id: String) : Module(id, ModuleType.CONJUNCTION) {
    private val mem = mutableMapOf<String, Pulse>()

    override fun connected(source: Module) {
        mem[source.id] = Pulse.LOW
    }

    override fun `in`(source: String, type: Pulse) {

        mem[source] = type

        if (mem.values.all { it == Pulse.HIGH }) {
            pulse(Pulse.LOW)
        } else {
            pulse(Pulse.HIGH)
        }
    }
}

class BroadcastModule(id: String) : Module(id, ModuleType.BROADCAST) {

    override fun `in`(source: String, type: Pulse) {
        pulse(type)
    }
}

class TestModule(id: String, private val message: Boolean = false) : Module(id, ModuleType.TEST) {
    override fun `in`(source: String, type: Pulse) {
    }
}


fun List<Module>.press(it: Int, pulsed: PulseCallback? = null) {

    Module.reset()

    repeat(it) {
        val button = this.find { module -> module.id == "button" }!!
        button.`in`("User pressed $it", Pulse.LOW)

        val queue = mutableListOf(button)
        while (queue.isNotEmpty()) {
            val top = queue.first()
            queue.removeAt(0)
            if (top.update(it, pulsed)) {
                queue.addAll(top.outputs)
            }
        }
    }

}

class Day20 {
    val input = File("app/src/main/kotlin/advent/input/day20.txt").readLines()

    fun part1() {
        val modules = Module.parseComplete(input)

        modules.press(1000)

        println("Day 20 part 1 : ${Module.lowPulses * Module.highPulses}")
    }

    fun part2() {
        val modules = Module.parseComplete(input)

        // The only module connected to rx is zh, which receives its input from:
        // vd, ns, bh and dl
        // To create a low pulse on rx, zh needs a high pulse on all inputs so
        // this heppens when vd, ns, bh and dl all have a high pulse on their output at the same time.
        // When we calculate the LCM of these cycles we knoe that this is the case 
        val m = mutableMapOf<String, Long>()
        val sources = listOf("vd", "ns", "bh", "dl")
        modules.press(10000) { loop, module, pulse ->
            if (pulse == Pulse.HIGH) {
                if (module.id in sources) {
                    m[module.id] = loop - m.getOrDefault(module.id, 0)
                    // println(m[module.id])
                }
            }
        }

        println("Day 20 part 2 : ${lcm(m.values.toList())}") // Should be 207787533680413
    }
}

fun run() {
    Day20().part1()
    Day20().part2()
}