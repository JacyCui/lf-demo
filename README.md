# $\lambda_F$: The Core Calculus of Synchronized Firrtl

This is a quick prototype of $\lambda_F$ semantics, including

- A parser of $\lambda_F$ circuit and script.

- An interpreter of $\lambda_F$ semantics.

- A demonstration test located at `src/test/java/pascal/lf/LFTest.java`.

- A simple commandline interface `./lf.sh`.

    ```
    Usage: ./lf.sh <circuit file> <script file>
    ```

## Kick-the-Tires

To exercise an easy example from command line, run

```shell
./lf.sh demo.circuit demo.script
```

