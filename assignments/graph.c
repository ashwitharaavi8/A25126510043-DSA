#include <stdio.h>
#define MAX 20

int graph[MAX][MAX];
int visited[MAX];
int queue[MAX];
int n;
int front = 0, rear = -1;

void BFS(int start) {
    int i, current;
    visited[start] = 1;
    queue[++rear] = start;
    printf("BFS Traversal: ");
    while (front <= rear) {
        current = queue[front++];
        printf("%d ", current);
        for (i = 0; i < n; i++) {
            if (graph[current][i] == 1 && visited[i] == 0) {
                visited[i] = 1;
                queue[++rear] = i;
            }
        }
    }
    printf("\n");
}

int main() {
    int i, j, start;
    printf("Enter number of vertices: ");
    scanf("%d", &n);
    printf("Enter adjacency matrix:\n");
    for (i = 0; i < n; i++) {
        for (j = 0; j < n; j++) {
            scanf("%d", &graph[i][j]);
        }
    }
    printf("Enter starting vertex (0 to %d): ", n - 1);
    scanf("%d", &start);
    for (i = 0; i < n; i++)
        visited[i] = 0;
    BFS(start);
    return 0;
}