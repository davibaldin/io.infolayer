# Infolayer Quickstart

First, get your environment up and running at [Setup](docs/Setup.md).

See of the complete list of known [plugins](docs/../Plugins.md).

## Create or connect to a Git repository.

Repository layout.

```
.
|--plugins
|--runbook
    |--<your file goes here>
```

You can initialize a new repository by executing:

```bash
mkdir repo
cd repo
git init
mkdir plugins runbook
```

## Create a Runbook

```yaml
name: Name of your Runbook
enabled: false
schedule: 0 */3 * ? * *
repository: name
tags: tag1, tag2, tag3

do:
    - plugin-name:
        param1: foo
        param2: bar
        param3: true

    - another-plugin-name:
        param1: foo
        do:
            plugin-name:
            target: ${env:variable}
            topPorts: 50
            timeout: 4320000
```
Save file on ```repo/runbook/first_runbook.yml```