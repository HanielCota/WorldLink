# Como Gerar Releases Automáticas

## 📦 Visão Geral

O projeto WorldLink agora possui **workflows automatizados** no GitHub Actions que criam releases automaticamente quando você faz push de tags de versão.

## 🚀 Como Criar uma Release

### Passo 1: Commit suas mudanças

Primeiro, certifique-se de que todas as suas alterações estão commitadas:

```bash
git add .
git commit -m "Descrição das mudanças"
git push origin main
```

### Passo 2: Criar uma tag de versão

Crie uma tag seguindo o padrão `v*.*.*` (exemplo: `v1.0.0`, `v1.2.3`, `v2.0.0`):

```bash
# Criar tag localmente
git tag v1.0.0

# Ou criar tag com mensagem
git tag -a v1.0.0 -m "Release version 1.0.0"
```

### Passo 3: Fazer push da tag

Envie a tag para o GitHub:

```bash
git push origin v1.0.0
```

### Passo 4: Aguarde a criação automática

O GitHub Actions irá automaticamente:

1. ✅ Detectar a nova tag
2. ✅ Fazer checkout do código
3. ✅ Configurar Java 21
4. ✅ Compilar o projeto com Gradle
5. ✅ Gerar o arquivo JAR
6. ✅ Criar uma release no GitHub
7. ✅ Anexar o arquivo JAR à release
8. ✅ Gerar notas de release automaticamente

## 📋 Workflows Disponíveis

### 1. Release Workflow (release.yml)

**Gatilho**: Push de tags no formato `v*.*.*`

**O que faz**:
- Compila o plugin
- Cria uma release no GitHub
- Anexa o JAR compilado
- Gera notas de release automaticamente a partir dos commits

**Exemplo de uso**:
```bash
git tag v1.0.0
git push origin v1.0.0
```

### 2. Build Workflow (build.yml)

**Gatilho**: Push para main/master ou Pull Requests

**O que faz**:
- Compila o código para garantir que está funcionando
- Salva os artifacts (JARs) por 7 dias
- Valida o código em PRs

## 🎯 Exemplos Práticos

### Exemplo 1: Primeira Release

```bash
# Fazer alterações no código
git add .
git commit -m "Feat: Adicionar novo comando de teleporte"
git push origin main

# Criar primeira release
git tag v1.0.0
git push origin v1.0.0
```

### Exemplo 2: Release com correção de bug

```bash
# Corrigir bug
git add .
git commit -m "Fix: Corrigir problema de teleporte para o Nether"
git push origin main

# Criar release patch
git tag v1.0.1
git push origin v1.0.1
```

### Exemplo 3: Nova funcionalidade (minor version)

```bash
# Adicionar nova funcionalidade
git add .
git commit -m "Feat: Adicionar suporte para mundos personalizados"
git push origin main

# Criar release minor
git tag v1.1.0
git push origin v1.1.0
```

### Exemplo 4: Breaking changes (major version)

```bash
# Mudanças que quebram compatibilidade
git add .
git commit -m "Breaking: Alterar formato de configuração"
git push origin main

# Criar release major
git tag v2.0.0
git push origin v2.0.0
```

## 📊 Visualização no GitHub

Após fazer push da tag, você pode:

1. Ir para a aba **Actions** do repositório
2. Ver o workflow "Create Release" em execução
3. Quando completar, ir para a aba **Releases**
4. Ver sua nova release com o JAR anexado!

## 🛠️ Padrão de Versionamento (SemVer)

Recomendamos seguir o Semantic Versioning:

- **MAJOR** (`v2.0.0`): Mudanças incompatíveis com versões anteriores
- **MINOR** (`v1.1.0`): Novas funcionalidades compatíveis
- **PATCH** (`v1.0.1`): Correções de bugs

## ⚠️ Dicas Importantes

1. **Sempre teste localmente** antes de criar uma release
2. **Use mensagens descritivas** nos commits para melhores release notes
3. **Não delete tags** que já foram pushed
4. **Verifique o workflow** na aba Actions se algo der errado

## 🔍 Verificar Status do Workflow

Para ver o status da criação da release:

1. Acesse: `https://github.com/HanielCota/WorldLink/actions`
2. Clique no workflow "Create Release"
3. Veja os logs detalhados de cada etapa

## 📞 Problemas Comuns

### Release não foi criada

- Verifique se a tag segue o padrão `v*.*.*`
- Certifique-se que o workflow tem permissões para criar releases
- Verifique os logs na aba Actions

### Build falhou

- Certifique-se que o código compila localmente com `./gradlew build`
- Verifique as dependências no `build.gradle`
- Veja os logs detalhados na aba Actions

## 📚 Documentação Adicional

- [GitHub Actions Documentation](https://docs.github.com/actions)
- [Semantic Versioning](https://semver.org/)
- [Git Tagging](https://git-scm.com/book/en/v2/Git-Basics-Tagging)
