#include <stdio.h>
#include <stdlib.h>
struct Node
{
    int roll;
    struct Node *next;
};
struct Node *head = NULL;
struct Node* createNode(int roll)
{
    struct Node *newNode;
    newNode = (struct Node*)malloc(sizeof(struct Node));
    newNode->roll = roll;
    newNode->next = NULL;
    return newNode;
}
void insertAtBeginning()
{
    int roll;
    printf("Enter roll number: ");
    scanf("%d", &roll);

    struct Node *newNode = createNode(roll);
    newNode->next = head;
    head = newNode;
}
void insertAtEnd()
{
    int roll;
    printf("Enter roll number: ");
    scanf("%d", &roll);
   struct Node *newNode = createNode(roll);
   if(head == NULL)
    {
        head = newNode;
        return;
    }
    struct Node *temp = head;
    while(temp->next != NULL)
        temp = temp->next;
    temp->next = newNode;
}
void insertAfter()
{
    int roll, after;
    printf("Enter roll number to insert: ");
    scanf("%d", &roll);
    printf("Enter roll number after which to insert: ");
    scanf("%d", &after);

    struct Node *temp = head;
    while(temp != NULL && temp->roll != after)
        temp = temp->next;

    if(temp == NULL)
    {
        printf("Roll number not found\n");
        return;
    }

    struct Node *newNode = createNode(roll);
    newNode->next = temp->next;
    temp->next = newNode;
}
void search()
{
    int roll;
    printf("Enter roll number to search: ");
    scanf("%d", &roll);
    struct Node *temp = head;
    while(temp != NULL)
    {
        if(temp->roll == roll)
        {
            printf("Roll number found\n");
            return;
        }
        temp = temp->next;
    }
    printf("Roll number not found\n");
}
void deleteNode()
{
    int roll;
    printf("Enter roll number to delete: ");
    scanf("%d", &roll);
    if(head == NULL)
    {
        printf("List is empty\n");
        return;
    }
   if(head->roll == roll)
    {
        struct Node *temp = head;
        head = head->next;
        free(temp);
        return;
    }
    struct Node *temp = head;
    struct Node *prev = NULL;
    while(temp != NULL && temp->roll != roll)
    {
        prev = temp;
        temp = temp->next;
    }
    if(temp == NULL)
    {
        printf("Roll number not found\n");
        return;
    }
    prev->next = temp->next;
    free(temp);
}

void display()
{
    if(head == NULL)
    {
        printf("List is empty\n");
        return;
    }
   struct Node *temp = head;
   while(temp != NULL)
    {
        printf("%d -> ", temp->roll);
        temp = temp->next;
    }
    printf("NULL\n");
}
int main()
{
    int choice;
     while(1)
    {
        printf("\n1. Insert at Beginning\n");
        printf("2. Insert at End\n");
        printf("3. Insert After\n");
        printf("4. Search\n");
        printf("5. Delete\n");
        printf("6. Display\n");
        printf("7. Exit\n");
        printf("Enter choice: ");
        scanf("%d", &choice);

        switch(choice)
        {
            case 1:
                insertAtBeginning();
                break;
            case 2:
                insertAtEnd();
                break;
            case 3:
                insertAfter();
                break;
            case 4:
                search();
                break;
            case 5:
                deleteNode();
                break;
            case 6:
                display();
                break;
            case 7:
                return 0;
            default:
                printf("Invalid choice\n");
        }
    }
}