# Спецификация 

**Суть Алгоритма:** Алгоритм находит кратчайшие пути от одной из вершин графа до всех остальных.

**Псевдокод:**

```
func dijkstra(s):
    for v ∈ V                    
        d[v] = ∞
        used[v] = false
    d[s] = 0
    for i ∈ V
        v = null
        for j ∈ V   // найдём вершину с минимальным расстоянием
            if !used[j] and (v == null or d[j] < d[v])
                v = j
        if d[v] == ∞
            break
        used[v] = true
        for e : исходящие из v рёбра     // произведём релаксацию по всем рёбрам, исходящим из v
            if d[v] + e.len < d[e.to]
                d[e.to] = d[v] + e.len
```

**Интерфейс приложения**

![](./images/app_desc.png)

**UML-диаграмма, демонстрирующая возвожности приложения**

![](./images/specification.png)
