import matplotlib.pyplot as graph
import numpy as np

Cycles, MemoryAddress = np.loadtxt('input.txt', delimiter=',', unpack=True)

graph.scatter(Cycles, MemoryAddress, color='red')
graph.title('Cycles vs Memory Address')
graph.xlabel('Cycles')
graph.ylabel('Memory Address')
graph.show()
graph.savefig('plot.png', dpi=700, bbox_inches='tight')


