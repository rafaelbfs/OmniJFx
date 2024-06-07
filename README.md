# OmniJFX

## About

OmniJavaFX has the aim to become a backoffice JavaFX applications platform.
To fulfill this aim OmniJFX will provide a Launcher (platform) and installers or packages for the latter on specific OSes, 
initially Linux but eventually for BSD, Windows and Mac.

## Development Philosophy

### 1 Code is a Liability (but a necessary one)
As paradoxical as it sounds, I stand 100% by this statement, each line of code is a potential bug (or worse, an attack surface) but it is inevitably an increase in the maintence burden, specially to new developers. Though very helpful, any clean code practice will never turn code into an asset, the best they can do is, as the name suggests, turn your codebase into a clean liability and minimize its onbearding time and maintenance burden but never reduce it to zero not even to a negligible amount. There are no unicorns and "happily everafter" in codebases.

**The asset is in the functionality that software provides.** The customer does not pay for your codebase's cleanliness nor simplicity, neither ease of maintenance, this is your problem not theirs, they pay for and only care about its function primarily .

### 2 Third-party Code (specially open-source) is just as bad, or worse
There is no greater proof of this point than Log4Shell exploit, in spite of not owning a single line of Log4J's code, IT departments of companies around the world had to scramble and work overtime to mitigate the Java Ecosystem's most nefarious exploit to date because it was that bad and could give privilege escalation with little effort to an attacker. If it was a proprietary library, it would have been the IT department of the library's mantaininer that would have scrambled otherwise the client's legal departments would have instead, to sue for damages.

Of course the case above is an extreme and pathological one and not the day-to-day reality of open-source software (the BSD family OSes are far superior than proprietary ones like Windows and MacOS will ever be)  but it does, however, illustrate a tradeoff that should be in every developer's minds, that of in-house development vs integration costs.

### Better than detecting an Error in Test/Development Runtime is to detect it in Compile Time, which brings us to...
### 3 Code-Metadata (Macros too) and Zero-Code Tools are worse than Code
As bad as it might be, at least code (specially that of compiled languages) is fully debuggable by modern development tools and some IDEs can even show its issues on the fly, before even building and running the program. This is not the case with metadata and zero-code tools, because in spite of the latter's name they do generate tons of code or, worse, use reflection, which not just punishes performance but security sometimes, either way their product is complicated or impossible (unless you're a hacker with a lot of time) to be debugged and can open even more terrible attack-surfaces. IDE templates/macros do not fall in this category if their generated code can be seen and changed, if necessary, before build.

In this fashion, **this platform rejects FXML altogether**, since it is code-metadata and clearly increases maintenance burden because now the user needs to deal with two totally coupled files, namely a `.java` and a `.fxml`, to define one component. I much prefer a programatic approach that leverages each programming language's abstractions capabilities to provide a smoother development experience compared to using the JavaFX API directly. This is what I am implementying in `jfx` for Java and in `kfx` for Kotlin, a thin and low cost abstraction layer (not zero-cost that the Rust cult tries to sell) that makes the user's life easier without reducing their freedom from being too opinionated. 
