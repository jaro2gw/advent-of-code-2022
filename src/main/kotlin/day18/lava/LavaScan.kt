package day18.lava

import utils.MinMax
import java.util.LinkedList

class LavaScan {
    private fun neighbours(droplet: LavaDroplet): List<LavaDroplet> = listOf(
        droplet.copy(x = droplet.x - 1),
        droplet.copy(x = droplet.x + 1),
        droplet.copy(y = droplet.y - 1),
        droplet.copy(y = droplet.y + 1),
        droplet.copy(z = droplet.z - 1),
        droplet.copy(z = droplet.z + 1),
    )

    private fun neighbours(
        droplet: LavaDroplet,
        xs: MinMax<Int>,
        ys: MinMax<Int>,
        zs: MinMax<Int>
    ): List<LavaDroplet> = neighbours(droplet).filter { (x, y, z) ->
        x in xs && y in ys && z in zs
    }

    fun sides(droplets: Set<LavaDroplet>): Int = droplets.sumOf { droplet ->
        neighbours(droplet).count { it !in droplets }
    }

    fun sidesWithoutBubbles(droplets: Set<LavaDroplet>): Int {
        var (xs, ys, zs) = droplets
            .map { (x, y, z) ->
                Triple(
                    MinMax(x),
                    MinMax(y),
                    MinMax(z),
                )
            }
            .reduce { (xs1, ys1, zs1), (xs2, ys2, zs2) ->
                Triple(
                    xs1 + xs2,
                    ys1 + ys2,
                    zs1 + zs2,
                )
            }

        xs = xs + (xs.min - 1) + (xs.max + 1)
        ys = ys + (ys.min - 1) + (ys.max + 1)
        zs = zs + (zs.min - 1) + (zs.max + 1)

        val boundary = mutableSetOf<LavaDroplet>()
        val exterior = mutableSetOf(LavaDroplet(xs.min, ys.min, zs.min))

        val queue = LinkedList(exterior)
        while (queue.isNotEmpty()) {
            val droplet = queue.remove()
            val (bnd, ext) = neighbours(droplet, xs, ys, zs).partition { it in droplets }
            boundary += bnd
            queue += ext.filter { exterior.add(it) }
        }

        return boundary.sumOf { droplet ->
            neighbours(droplet).count { it in exterior }
        }
    }
}
