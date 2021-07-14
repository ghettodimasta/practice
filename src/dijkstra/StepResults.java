package dijkstra;

public class StepResults {
    public Node currentNode;
    public Node lookingNode;
    Integer price;
    String result;
    Integer shortest_before;
    Integer shortest_after;

    public String getResult() {
        this.result = "Текущая вершина : " + this.currentNode.getName() + "\nПросматриваемая вершина: " +
                this.lookingNode.getName() + "\nСтоимость пути: " + this.price + "\nИзначальный кратчайший путь: " +
        this.shortest_before + "\nТекущий кратчайший путь: " + this.shortest_after;
        return this.result;
    }
}
