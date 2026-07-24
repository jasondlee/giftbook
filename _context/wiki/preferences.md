# Working Preferences

## Development Standards

### Code Style and Conventions

- **Follow Kotlin conventions**: Adhere to official Kotlin coding standards
- **Clean code principles**: Keep code readable, maintainable, and well-organized
- **Meaningful names**: Use descriptive variable, function, and class names
- **Code organization**: Logical grouping of related functionality

### Documentation

- **Document complex logic**: Add comments for non-obvious implementations
- **Self-documenting code**: Prefer clear code structure over excessive comments
- **API documentation**: Document public interfaces and key functions
- **Architecture decisions**: Record significant technical choices

### Testing Approach

- **Test critical paths**: Focus on core functionality and business logic
- **Pragmatic testing**: Balance coverage with development speed
- **Integration tests**: Verify key workflows work end-to-end

### Architecture Patterns

- **MVVM/MVI**: Use appropriate patterns for UI state management
- **Dependency Injection**: Leverage Koin for clean dependency management
- **Repository pattern**: Separate data access from business logic
- **Compose best practices**: Follow Compose Multiplatform guidelines

## Communication Preferences

### Working with AI Assistants

- **Ask when uncertain**: Don't make assumptions about unclear requirements
- **Concise explanations**: Provide brief, focused explanations of changes
- **Action-oriented**: Focus on getting things done efficiently
- **Reasonable assumptions**: Make sensible decisions for minor details, but clarify major ones

### Response Style

- **Direct and technical**: Skip unnecessary pleasantries, get to the point
- **Explain reasoning**: Briefly explain why you're taking a specific approach
- **Show alternatives**: When relevant, mention other options considered
- **Highlight trade-offs**: Point out important decisions and their implications

## Project-Specific Guidelines

### Code Changes

- **Incremental updates**: Make focused, reviewable changes
- **Maintain consistency**: Follow existing patterns in the codebase
- **Cross-platform awareness**: Consider both Android and iOS implications
- **Database migrations**: Handle schema changes carefully

### Problem-Solving Approach

1. Understand the requirement clearly
2. Check existing code for patterns to follow
3. Propose solution if significant architectural impact
4. Implement with appropriate testing
5. Verify cross-platform compatibility

### What to Avoid

- **Over-engineering**: Keep solutions simple and practical
- **Breaking changes**: Avoid unnecessary refactoring of working code
- **Platform-specific hacks**: Prefer proper KMP abstractions
- **Verbose explanations**: Keep communication efficient and focused
