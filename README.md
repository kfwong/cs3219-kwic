# CS3219 Assignment 1 Report: Keyword in Context (KWIC)

![demo](https://cdn.rawgit.com/kfwong/cs3219-kwic/a689ef2b/gif/demo.gif)

Code Repository URL:
[https://github.com/kfwong/cs3219-kwic](https://github.com/kfwong/cs3219-kwic)

Author: Wong Kang Fei (A0138862W)

## Introduction
KWIC (Key Word In Context) index system provides a search mechanism for
information in a long list of lines, such as movie titles. Our team explored
**Pipe & Filter** and xxxx and implemented these design in our program.

## Requirements
### Functional
- The program should be able to display a list of KWIC to user.
- The program is able to perform circular shifting and sorting of entries.
- The program should display the average response time upon displaying result.
- The program should exclude duplicate entries in the result.

### Non-Functional
#### Interface
- The primary input and output should be done within a command-line interface.
- The interface should not display debug or exception details to the user.
(i.e. NullPointerException)
- The program allows input from keyboard or text files.

#### Performance & Reliability
- Given a list of 500 entries (maximum of 3 phrases per entries), the program
should be able to calculate result and display to user within 2 seconds on an
Intel i7 CPU.
- The program should handle data buffering internally without interrupting user
interaction (i.e. Blocking I/O).

#### Portability
- The program should be implemented in Java language (version 8 or above).
- The program should run on platform with Java JVM installed. (eg: Windows,
Linux, MacOS)

#### Usability
- The program should provide intruction guide upon startup.
- The program should notify the user with message when exiting.

#### Maintainability
- Each module should be unit-testable.

## Architectural Design
### Pipe & Filter
According to my understanding, this particular architecture can be related to
other similar paradigm such as "piping" in Unix command, where the output of
the previous command is taken as an input for the next command to process. It
can also be related to the plain-old functional programming where the data is
evaluated by composite of functions to produce result.

The program is designed closely according to the description in publication
under Garlan & Shaw. Figure 1 shows the general workflow.

*(Figure 1 - Dataflow Diagram)*

![dataflow-diagram](https://cdn.rawgit.com/kfwong/cs3219-kwic/7f0f4cf5/svg/cs3219-assignment-1-dataflow.svg)

The architecture consists of 4 main modules, mainly **Input**, **Circular
Shift**, **Alphabetizer** and **Output**. The **Pipe** (depicted as arrows)
acts as an intermediate buffer to facilitate communication between modules.

Figure 2 shows the Class Diagram for the underlying program design.

*(Figure 2 - Class Diagram)*

![class-diagram](https://cdn.rawgit.com/kfwong/cs3219-kwic/7f0f4cf5/svg/cs3219-assignment-1-class-diagram.svg)

**DataSink** act as the input source of the pipeline. It handles reading input
from users and transform it into readable format for the subsequent module.

**Filter** is an abstract class mainly handles transformation of the data. It
defines two pipes for incoming and outgoing information. It is expected to be
extended by specialized module such as **CircularShift** and **Alphabetizer**.

**DataSink** is the final node in the pipeline. It handles displaying by
writing the data to the output console.

**Runnable** is an interface implemented by all modules in the pipeline. This
allows the module to be run in separate thread.

**Pipe** acts as the facilitator between two modules. It buffers data using a
Queue data structure.

**Pipeline** is the master control of the process. It register all modules and
assign running threads to each modules.

The following Sequence Diagram (Figure 3) describes the general program
behaviour.

*(Figure 3 - Sequence Diagram)*

![sequence-diagram](https://cdn.rawgit.com/kfwong/cs3219-kwic/38de20eb/svg/cs3219-assignment-1-sequence-diagram.svg)
*(Figure 3)*

The sequence diagram assumes that all required objects have already been
instantiated.

#### Init Phase
During the init phase, the Pipeline register all modules. Note that the
registration order of the **Filter** is important as it dictates the order of
execution for the data transformation.

#### Loop Phase
Once the Pipeline call to its run() method, it assigns each registered module
into separate threads.

The **Datasource** will read user input and push to the pipe immediately after
receiving it.

The **Filter** module detects that there are data in the incoming pipe and
therefore pulling the data from the buffer for processing. After that, the
result is again pushed to the next pipe's buffer. This process can repeat
multiple times across multiple filters until it reaches a **DataSink**

The final step is reached when the data reaches a **DataSink**. The result is
written to the output console.

The Pipeline is expected to loop until break conditions are met (i.e. pressing
<kbd>CTRL + D</kbd>). Upon breaking the loop, the program will be terminated
and all module threads will be killed.

Note that the pipelining process does not wait for the data to traverse the
entire pipeline before fetching the next instruction. This means that as soon
as a module is done with processing current data and push it to the next pipe,
it will immediately fetch the next data from the incoming pipe for processing.

## Limitation & Benefits of Selected Designs
### Flexibility
The **Pipe** is a generic class which accept predefined type of value upon
instantiation. It wraps the specified type in a Queue to create a buffer. Such
design allows the developer to specify any type of value flowing through the
pipe without restricting to one specific type (i.e. String).

Similarly, the abstract class **Filter** is also a generic class. The developer
can specify different incoming and outgoing type for the pipes, which greatly
increase the flexilibity of how the underlying algorithm can be designed.

### Extensibility
It is not difficult to observe that there are multiple instances of different
filters in the pipeline. The **Filter** abstract class provides the boilerplate
for specilized filter to extend. It predefines the incoming & outgoing pipes
and leave the underlying *filter()* algorithm to its subclasses.

All modules also implements the Runnable interface provided by Java to allow
multithreading capability.

### Modularity
The Pipe & Filter architecture creates highly independent modules. As such, the
design should be highly cohesive where the filters can be removed or added with
minimal changes.

### Testing
High modularity often leads to easier testing. All modules are designed to be
unit-testable. The implementation minimizes the dependencies to achive low
coupling so that it can be easily tested or develop separately.

### Performance
A typical Pipe & Filter architecture without threading capability is often slow
because it has to wait for the data to traverse the pipeline before fetching
the next instruction. The current implementation uses threading to process data
in stream which increases the speed of processing.

A special consideration in Pipe & Filter design is that the possibility of a
bottleneck module. It is the responsibility of the module designer to minimize
the blocking operation such as I/O operation.

On the other hand, long running operation or low efficiency algorithms can
"choke" the pipeline. For example, the **Alphabetizer** module handles sorting.
Due to the data streaming capability in the current design, it is unwise to use
sorting algorithm that relies on defined size of elements (Merge Sort or Quick
Sort). A different sorting strategy must be use, such as replacing it with Heap
Sort. Note that the current design uses TreeSet which guarantees the
**Alphabetizer** O(log N) sorting capability on streaming data.

### Fluent API
The **Pipeline** class uses builder pattern to achieve fluent programming
interface. This allows more intuitive usage and closely represent to the
original Pipe & Filter architecture.

```java
// fluent api using builder pattern
Pipeline pipeline = new Pipeline();
pipeline.generateFrom(dataSource)
        .transformBy(circularShift)
        .transformBy(alphabetizer)
        .outputInto(dataSink)
        .run();
```

## Other information
The initial test on the performance is moderately responsive. The test data
consists of the entries from [Project Gutenberg's Fifteen Thousand Useful
Phrases, by Greenville
Kleiser](http://www.gutenberg.org/cache/epub/18362/pg18362.txt).

| Number of entries | Minimum phrases per entries | Maximum phrases per entries | Processing TIme (seconds) |
| ----------------- | --------------------------- | --------------------------- | ------------------------- |
| 500               | 2                           | 2                           | 1                         |
| 1000              | 2                           | 2                           | 4.2                       |
| 2000              | 4                           | 10                          | 19                        |
