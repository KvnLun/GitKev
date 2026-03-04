# GitKev — A Minimal Git Implementation in Java

GitKev is a **minimal version control system** written in Java that recreates the core ideas behind Git:

- content-addressable storage
- snapshot-based commits
- commit history traversal
- repository checkout (time travel)

The goal of this project is educational: to understand **how Git works internally** by building a simplified implementation from scratch.

---

##  Features (MVP)

✅ Initialize a repository  
✅ Stage files  
✅ Create commits using SHA-1 hashes  
✅ View commit history (`log`)  
✅ Restore previous versions (`checkout`)  
✅ Persistent object storage  

---

##  Core Concept

GitKev follows Git’s fundamental design:

> A commit represents a snapshot of the project at a moment in time.

Each commit stores:
- commit message
- parent commit reference
- tracked file contents

Commits are identified using a SHA-1 hash derived from their contents.

---

##  Repository Structure

After initialization:

```bash
.gitkev/
├── objects/ # Stored objects (commits/files)
├── refs/
│ └── main # Current branch pointer (HEAD)
└── index # Staging area
```

---

##  How It Works

###  Init

Creates repository metadata.

```bash
java App init
```
Creates:

.gitkev/
### Add (Stage Files)

Adds files to the staging area.
```bash
java App add hello.txt
```
Updates:

.gitkev/index
### Commit

Creates a snapshot of staged files.
```bash
java App commit "Initial commit"
```
Process:

Read staged files

Build commit object

Hash commit contents (SHA-1)

Store object permanently

Update refs/main

Clear staging area

### Log

Traverses commit history via parent references.
```bash
java App log
```
Output example:

Commit: bca6c3f...
Message: Second commit

Commit: eaa79ad...
Message: First commit
### Checkout (Time Travel)

Restore project state from a previous commit.
```bash
java App checkout <commit-hash>
```
Process:

Load commit object

Read stored files

Rewrite working directory

Your files now match that historical snapshot.

##  Architecture Overview
```bash
Working Directory
        ↓
     (add)
        ↓
      Index
        ↓
     (commit)
        ↓
   Object Store
        ↓
      Checkout
        ↓
Working Directory (restored)
```
## Key Design Ideas
Content Addressing

Objects are stored using:

hash = SHA1(object contents)

This guarantees:

identical content → identical hash

immutable history

efficient storage model

Snapshot Model

Unlike diff-based systems, GitKev stores complete snapshots per commit.

Commit A → hello.txt = "hi"
Commit B → hello.txt = "hi world"
🔬 Comparison to Real Git
GitKev	Git
Java objects	Blob/Tree objects
.gitkev folder	.git directory
SHA-1 commits	SHA-1/SHA-256
Single branch	Multiple branches
Basic checkout	Full working tree

GitKev implements the core mental model behind Git while omitting advanced optimizations.

## Build & Run

Compile:

javac App.java commands/*.java storage/*.java objects/*.java

Run commands:

java App init
java App add <file>
java App commit "message"
java App log
java App checkout <hash>
📚 What This Project Demonstrates

File system manipulation in Java

Hash-based storage systems

Immutable data structures

Version control internals

Commit graph traversal

Systems design fundamentals

## Future Improvements

### Planned enhancements:

 Branch support

 HEAD pointer abstraction

 Tree/blob object separation

 Diff-based storage

 Merge support

 Status command

 Ignore files (.gitignore equivalent)

## Learning Goal

This project exists to answer:

“What is Git actually doing when I commit or checkout?”

By rebuilding Git’s core ideas, GitKev provides a deeper understanding of modern version control systems.

📄 License

MIT License
