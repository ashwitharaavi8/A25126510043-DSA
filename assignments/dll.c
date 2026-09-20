#include <stdio.h>
#include <stdlib.h>
#include <string.h>

struct node {
    char url[100];
    struct node *prev;
    struct node *next;
};

struct node *head = NULL;
struct node *tail = NULL;
struct node *current = NULL;

void insertPage() {
    struct node *newnode = (struct node *)malloc(sizeof(struct node));
    if (newnode == NULL) {
        printf("Memory not available\n");
        return;
    }

    printf("Enter page URL: ");
    scanf("%s", newnode->url);
    newnode->prev = NULL;
    newnode->next = NULL;

    if (head == NULL) {
        head = newnode;
        tail = newnode;
        current = newnode;
    }
    else if (current == tail) {
        newnode->prev = tail;
        tail->next = newnode;
        tail = newnode;
        current = newnode;
    }
    else {
        newnode->next = current->next;
        newnode->prev = current;
        current->next->prev = newnode;
        current->next = newnode;
        current = newnode;
    }
    printf("Page inserted\n");
}

void moveForward() {
    if (head == NULL) {
        printf("No pages in history\n");
    }
    else if (current->next == NULL) {
        printf("You are at the last page, cannot move forward\n");
    }
    else {
        current = current->next;
        printf("Moved forward\n");
    }
}

void moveBackward() {
    if (head == NULL) {
        printf("No pages in history\n");
    }
    else if (current->prev == NULL) {
        printf("You are at the first page, cannot move backward\n");
    }
    else {
        current = current->prev;
        printf("Moved backward\n");
    }
}

void deletePage() {
    char key[100];
    struct node *temp;

    if (head == NULL) {
        printf("No pages in history\n");
        return;
    }

    printf("Enter page URL to delete: ");
    scanf("%s", key);

    temp = head;
    while (temp != NULL && strcmp(temp->url, key) != 0) {
        temp = temp->next;
    }

    if (temp == NULL) {
        printf("Page not found\n");
        return;
    }
    if (temp == current) {
        if (temp->next != NULL)
            current = temp->next;
        else
            current = temp->prev;
    }

    if (temp == head && temp == tail) {
        head = NULL;
        tail = NULL;
    }
    else if (temp == head) {
        head = temp->next;
        head->prev = NULL;
    }
    else if (temp == tail) {
        tail = temp->prev;
        tail->next = NULL;
    }
    else {
        temp->prev->next = temp->next;
        temp->next->prev = temp->prev;
    }

    free(temp);
    printf("Page deleted\n");
}

void displayForward() {
    struct node *temp = head;

    if (head == NULL) {
        printf("No pages in history\n");
        return;
    }

    printf("Pages (first to last): ");
    while (temp != NULL) {
        printf("%s", temp->url);
        if (temp->next != NULL)
            printf(" <-> ");
        temp = temp->next;
    }
    printf("\n");
}

void displayBackward() {
    struct node *temp = tail;

    if (tail == NULL) {
        printf("No pages in history\n");
        return;
    }

    printf("Pages (last to first): ");
    while (temp != NULL) {
        printf("%s", temp->url);
        if (temp->prev != NULL)
            printf(" <-> ");
        temp = temp->prev;
    }
    printf("\n");
}

int main() {
    int choice;

    do {
        printf("\n--- Browser History ---\n");
        printf("1. Insert new page\n");
        printf("2. Move forward\n");
        printf("3. Move backward\n");
        printf("4. Delete a page\n");
        printf("5. Display first to last\n");
        printf("6. Display last to first\n");
        printf("7. Exit\n");
        printf("Enter your choice: ");
        scanf("%d", &choice);

        switch (choice) {
            case 1: insertPage(); break;
            case 2: moveForward(); break;
            case 3: moveBackward(); break;
            case 4: deletePage(); break;
            case 5: displayForward(); break;
            case 6: displayBackward(); break;
            case 7: printf("Exiting...\n"); break;
            default: printf("Invalid choice\n");
        }
    } while (choice != 7);

    return 0;
}