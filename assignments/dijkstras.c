#include <stdio.h>
#define MAX 20
#define INF 99999

void dijkstra(int graph[MAX][MAX], int n, int source) {
    int distance[MAX];
    int visited[MAX];
    int i, j, count;
    int min, u;
    for (i = 0; i < n; i++) {
        distance[i] = graph[source][i];
        visited[i] = 0;
    }
    distance[source] = 0;
    visited[source] = 1;
    for (count = 1; count < n; count++) {
        min = INF;
        u = -1;
        /* Find the unvisited vertex with minimum distance */
        for (i = 0; i < n; i++) {
            if (!visited[i] && distance[i] < min) {
                min = distance[i];
                u = i;
            }
        }
        if (u == -1)
            break;
        visited[u] = 1;
        /* Update distances */
        for (j = 0; j < n; j++) {
            if (!visited[j] &&
                graph[u][j] != 0 &&
                distance[u] + graph[u][j] < distance[j]) {
                distance[j] = distance[u] + graph[u][j];
            }
        }
    }
    printf("\nShortest distances from vertex %d:\n", source);
    for (i = 0; i < n; i++) {
        if (distance[i] == INF)
            printf("Vertex %d -> Unreachable\n", i);
        else
            printf("Vertex %d -> %d\n", i, distance[i]);
    }
}

int main() {
    int graph[MAX][MAX];
    int n, source;
    int i, j;

    printf("Enter number of vertices: ");
    scanf("%d", &n);

    printf("Enter weighted adjacency matrix:\n");
    printf("(Enter 0 if there is no edge)\n");

    for (i = 0; i < n; i++) {
        for (j = 0; j < n; j++) {
            scanf("%d", &graph[i][j]);
            if (graph[i][j] == 0 && i != j)
                graph[i][j] = INF;
        }
    }
    printf("Enter source vertex (0 to %d): ", n - 1);
    scanf("%d", &source);
    dijkstra(graph, n, source);
    return 0;
}