# UI Structure Analysis

## Current Architecture

The UI follows a consistent **Component/Content** pattern based on Decompose architecture:

- **Component files**: Business logic, state management, navigation, data operations
- **Content files**: Composable UI rendering, user interactions

## Screen Types and Patterns

### 1. View Screens
Files: `ViewOccasion`, `ViewRecipient`, `ViewOccasionRecip`

**Common Structure:**
```kotlin
// Component
- lateinit var entity: Entity
- var requestStatus = MutableValue(Status.LOADING)
- var relatedData: MutableValue<List<T>> = MutableValue(emptyList())
- init { doOnResume { load data } }
- fun edit() { nav.pushToFront(...) }
- fun delete() { dao.delete(...); nav.pop() }

// Content
- val status by component.requestStatus.subscribeAsState()
- val deleteDialog = remember { mutableStateOf(false) }
- ConfirmationDialog setup
- AsyncLoad(status) { ... }
- AddEditHeader(label, editClick, deleteClick)
- LazyColumn with items
- ActionButton for adding related items
```

### 2. Add/Edit Screens
Files: `AddEditOccasion`, `AddEditRecipient`, `AddEditIdea`, `AddEditOccasionRecipient`

**Common Structure:**
```kotlin
// Component
- val form = SomeForm(entity)
- fun save() { validate, create/update entity, nav.pop() }
- fun cancel() { nav.pop() }

// Content
- Column with form fields
- TextField/ComboBoxField/DateField components
- Save/Cancel button row (IDENTICAL across all files)
```

### 3. List Screens
Files: `OccasionList`, `RecipientList`

**Common Structure:**
```kotlin
// Component
- var items: MutableValue<List<T>> = MutableValue(emptyList())
- var requestStatus = MutableValue(Status.LOADING)
- init { doOnResume { load items } }

// Content
- AsyncLoad(status) { ... }
- LazyColumn with header + items
- DividingLine between items
- ActionButton for adding new items
```

## Identified Code Duplication

### 1. Save/Cancel Button Row (HIGH PRIORITY)
**Location**: Found in 5 files
- `AddEditOccasionContent.kt` (lines ~50-65)
- `AddEditRecipientContent.kt` (lines ~30-45)
- `AddEditIdeaContent.kt` (lines ~45-60)
- `AddEditOccasionRecipientContent.kt` (lines ~80-95)

**Duplicated Code:**
```kotlin
Row(modifier = Modifier.padding(top = 5.dp).fillMaxWidth()) {
    Button(
        onClick = { component.save() },
        modifier = Modifier.padding(end = 3.dp).fillMaxWidth(0.5f)
    ) {
        Text("Save")
    }
    Button(
        onClick = { component.cancel() },
        modifier = Modifier.padding(start = 3.dp).fillMaxWidth()
    ) {
        Text("Cancel")
    }
}
```

**Proposed Solution:**
Create `ui/general/SaveCancelButtons.kt`:
```kotlin
@Composable
fun SaveCancelButtons(
    onSave: () -> Unit,
    onCancel: () -> Unit,
    modifier: Modifier = Modifier,
    saveText: String = "Save",
    cancelText: String = "Cancel"
)
```

### 2. Delete Confirmation Dialog Setup (MEDIUM PRIORITY)
**Location**: Found in 3 files
- `ViewOccasionContent.kt`
- `ViewRecipientContent.kt`
- `ViewOccasionRecipContent.kt`

**Duplicated Pattern:**
```kotlin
val deleteDialog = remember { mutableStateOf(false) }
if (deleteDialog.value) {
    ConfirmationDialog(
        onDismissRequest = { deleteDialog.value = false },
        onConfirmation = {
            deleteDialog.value = false
            component.deleteAction()
        },
        dialogTitle = "Confirmation",
        dialogText = "Are you sure you want to delete X?",
        icon = Icons.Filled.QuestionMark
    )
}
```

**Proposed Solution:**
Create `ui/general/DeleteConfirmation.kt`:
```kotlin
@Composable
fun DeleteConfirmationDialog(
    showDialog: MutableState<Boolean>,
    itemName: String,
    onConfirm: () -> Unit
)
```

### 3. Component Initialization Pattern (LOW PRIORITY)
**Location**: Most Component files

**Duplicated Pattern:**
```kotlin
init {
    componentContext.doOnResume {
        CoroutineScope(Dispatchers.IO).launch {
            requestStatus.update { Status.LOADING }
            // load data
            requestStatus.update { Status.SUCCESS }
        }
    }
}
```

**Proposed Solution:**
Create base component helper or extension function for common initialization pattern.

### 4. AsyncLoad + ActionButton Pattern (LOW PRIORITY)
**Location**: Multiple Content files

**Pattern:**
```kotlin
AsyncLoad(status) {
    // content
}
ActionButton(onClick = { ... })
```

Often these are used together but ActionButton is outside AsyncLoad, which could be confusing.

## Structural Inconsistencies

### 1. Header Patterns
- **ViewOccasion, ViewRecipient**: Use `AddEditHeader` component ✓
- **ViewOccasionRecip**: Uses `AddEditHeader` ✓
- **AddEdit screens**: Use plain `Text` with varying font sizes (24sp, 30sp)

**Recommendation**: Standardize header component usage across all screens.

### 2. List Item Click Handlers
- Some use `.clickable { nav.bringToFront(...) }`
- Some use `.clickable { component.someMethod() }` which then navigates

**Recommendation**: Standardize on component methods for better testability.

### 3. Spacing and Padding
- Most use `Arrangement.spacedBy(16.dp)` for Column spacing ✓
- Some use manual `.padding()` on individual items
- Inconsistent use of `.padding(10.dp)` vs `.padding(5.dp)` for screen-level padding

**Recommendation**: Define standard spacing constants.

### 4. Font Sizes
- Headers: 24sp, 30sp (inconsistent)
- Body text: 18sp, 20sp, 24sp (inconsistent)
- Notes/secondary: 18sp with FontStyle.Italic

**Recommendation**: Define typography scale in theme.

## Proposed Refactoring Plan

### Phase 1: Extract Common UI Components (Immediate)
1. **SaveCancelButtons** component
2. **DeleteConfirmationDialog** wrapper
3. **StandardHeader** component for Add/Edit screens
4. **ListItemDivider** (already exists as `DividingLine`, ensure consistent usage)

### Phase 2: Standardize Patterns (Short-term)
1. Consistent header font sizes (30sp for main headers)
2. Consistent body text sizes (24sp for primary, 18sp for secondary)
3. Consistent padding (10.dp for screen-level, 5.dp for internal spacing)
4. Standardize click handler patterns (always through component methods)

### Phase 3: Component Base Classes (Medium-term)
1. Create base component interfaces for common CRUD operations
2. Extract common initialization patterns
3. Standardize error handling and loading states

### Phase 4: Advanced Improvements (Long-term)
1. Consider ViewModel pattern for complex state management
2. Extract navigation logic to dedicated navigation manager
3. Implement consistent animation/transition patterns

## Files Requiring Changes

### High Priority (Phase 1)
- Create: `ui/general/SaveCancelButtons.kt`
- Create: `ui/general/DeleteConfirmationDialog.kt`
- Create: `ui/general/StandardHeader.kt`
- Modify: All AddEdit Content files (5 files)
- Modify: All View Content files (3 files)

### Medium Priority (Phase 2)
- Modify: All Content files for consistency (12+ files)
- Update: `theme/` package with typography and spacing constants

### Low Priority (Phase 3-4)
- Refactor: Component base patterns
- Refactor: Navigation patterns

## Recommendations

1. **Start with SaveCancelButtons**: Highest impact, lowest risk
2. **Follow with DeleteConfirmationDialog**: Clear pattern, easy to extract
3. **Standardize incrementally**: Don't refactor everything at once
4. **Test after each change**: Ensure no regressions
5. **Document patterns**: Update this wiki with adopted patterns

## Notes

- Current code is well-organized and follows good separation of concerns
- Decompose architecture is properly implemented
- Main issue is code duplication, not architectural problems
- Refactoring should focus on DRY principle while maintaining clarity
- All changes should maintain cross-platform compatibility (Android/iOS)
