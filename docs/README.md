# Tux User Guide


![Screenshot of Tux](\docs\Ui.png)

Tux is a simple Java applications that helps you keep track of your tasks.

## Add todos

You can add a todo task by typing the command:

Example: `todo buy bread`

Expected outcome:

```
Got it. I've added this task:
[T][ ] buy bread
Now you have 3 tasks in the list.
```


## Adding deadlines

You can add a deadline task by typing the command:

Example: `deadline do homework /by 2025-01-02`

Expected outcome:

```
Got it. I've added this task:
[D][ ] do homework (by: Jan 2 2025)
Now you have 3 tasks in the list.
```


## Adding events

You can add a deadline task by typing the command:

Example: `event hackathon /from 2025-01-01 /to 2025-01-03`

Expected outcome:

```
Got it. I've added this task:
[E][ ] hackathon from: Jan 01 2025 to: Jan 03 2025
Now you have 3 tasks in the list.
```

## Marking and unmarking tasks

You can mark, or complete, and unmark a task by typing the command:

Example: `mark 2`

Expected outcome:

```
Nice! I've marked this task as done:
[T][X] buy bread
```

## Delete tasks

You can delete a task by typing the command:

Example: `delete 1`


Expected outcome:
```
Noted, I've removed this task:
[T][X] buy bread
Now you have 3 tasks in the list.
```

## Find tasks

You can search for tasks containing a keyword in their description.

Example: `find party`

Expected outcome:
```
Here are the matching tasks in your list:
[E][X] attend party from: Jan 01 2025 to: Jan 02 2025
```

# Get reminders for upcoming tasks

You can get a reminder for all tasks due within the next 7 days.

Example: `remind`

Expected outcome:
```
Here are the upcoming events:
[E][ ] attend party from: Jan 01 2025 to: Jan 02 2025

Here are the upcoming deadlines:
[D][ ] submit homework (by: Sep 15 2025)

```

## List your tasks

You can get a complete list of your tasks by typing the command:

Example: `list`

Expected outcome:
```
1. [E][ ] attend party from: Jan 01 2025 to: Jan 02 2025
2. [T][X] buy bread
3. [D][ ] submit homework (by: Sep 15 2025)
```