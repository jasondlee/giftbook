# UI Patterns and Standards

This document describes the standardized patterns and components used throughout the GiftBook UI.

## Common Components

### SaveCancelButtons
**Location**: `ui/general/SaveCancelButtons.kt`

Standard button row for all Add/Edit screens.

**Usage:**
```kotlin
SaveCancelButtons(
    onSave = { component.save() },
    onCancel = { component.cancel() }
)
```

**Parameters:**
- `onSave: () -> Unit` - Save action callback
- `onCancel: () -> Unit` - Cancel action callback
- `saveText: String = "Save"` - Optional custom save button text
- `cancelText: String = "Cancel"` - Optional custom cancel button text

### DeleteConfirmationDialog
**Location**: `ui/general/DeleteConfirmationDialog.kt`

Standard confirmation dialog for delete operations.

**Usage:**
```kotlin
val showDialog = remember { mutableStateOf(false) }

DeleteConfirmationDialog(
    showDialog = showDialog,
    itemName = entity.name,
    onConfirm = { component.delete() }
)

// Trigger with:
IconButton(onClick = { showDialog.value = true })
```

**Parameters:**
- `showDialog: MutableState<Boolean>` - Dialog visibility state
- `itemName: String` - Name of item being deleted (shown in message)
- `onConfirm: () -> Unit` - Confirmation callback
- `dialogTitle: String = "Confirmation"` - Optional custom title
- `icon: ImageVector = Icons.Filled.QuestionMark` - Optional custom icon

### StandardHeader
**Location**: `ui/general/StandardHeader.kt`

Consistent header for Add/Edit screens.

**Usage:**
```kotlin
StandardHeader(text = "Add New Recipient")
```

**Parameters:**
- `text: String` - Header text
- `modifier: Modifier = Modifier` - Optional modifier

### AddEditHeader
**Location**: `ui/general/AddEditHeader.kt`

Header with edit/delete actions for View screens.

**Usage:**
```kotlin
AddEditHeader(
    label = "Occasion Details",
    editClick = { component.edit() },
    deleteClick = { showDialog.value = true }
)
```

### Other Reusable Components
- **ActionButton**: Floating action button (typically for "Add" actions)
- **DividingLine**: Horizontal divider between list items
- **OccasionProgressRow**: Progress indicator for occasion tracking
- **GiftCostDialog**: Dialog for entering gift cost

## Design System

### Typography
**Location**: `theme/Dimensions.kt`

Standard text sizes:
```kotlin
Typography.headerSize        // 30.sp - Main headers
Typography.primaryTextSize   // 24.sp - Primary content
Typography.secondaryTextSize // 18.sp - Secondary content
Typography.smallTextSize     // 10.sp - Labels and hints
```

**Usage:**
```kotlin
Text(
    text = "Header",
    fontSize = Typography.headerSize,
    fontWeight = FontWeight.Bold
)
```

### Spacing
**Location**: `theme/Dimensions.kt`

Standard spacing values:
```kotlin
Spacing.screenPadding              // 10.dp - Screen-level padding
Spacing.internalPadding            // 5.dp - Internal component padding
Spacing.fieldSpacing               // 16.dp - Between form fields
Spacing.buttonPadding              // 3.dp - Button group padding
Spacing.listItemBottomPadding      // 10.dp - List item spacing
Spacing.cardPadding                // 15.dp - Card content padding
Spacing.dividerHorizontalPadding   // 5.dp - Divider horizontal
Spacing.dividerVerticalPadding     // 5.dp - Divider vertical
```

**Usage:**
```kotlin
Column(
    modifier = Modifier.padding(Spacing.screenPadding),
    verticalArrangement = Arrangement.spacedBy(Spacing.fieldSpacing)
)
```

### Icon Sizes
**Location**: `theme/Dimensions.kt`

```kotlin
IconSize.navigationIcon  // 36.dp - Bottom navigation
IconSize.listIcon        // 48.dp - List item icons
```

## Component Architecture

### Component/Content Pattern

All screens follow the Decompose architecture pattern:

**Component File** (`*Component.kt`):
- Business logic
- State management
- Data operations
- Navigation

**Content File** (`*Content.kt`):
- UI rendering
- User interactions
- Composable functions

### Component Initialization Pattern

Use `loadOnResume` extension for standard data loading:

```kotlin
import com.steeplesoft.giftbook.ui.loadOnResume

init {
    loadOnResume(requestStatus) {
        val data = dao.getData()
        items.update { data }
    }
}
```

### Coroutine Helpers

Use helper functions for cleaner coroutine code:

```kotlin
import com.steeplesoft.giftbook.ui.onMain
import com.steeplesoft.giftbook.ui.onIO

fun save() {
    onMain {
        dao.save(entity)
        nav.pop()
    }
}

fun loadData() {
    onIO {
        val data = dao.getData()
        items.update { data }
    }
}
```

## Screen Types

### List Screens
**Pattern:**
- AsyncLoad wrapper for loading state
- LazyColumn with header
- Items with click handlers
- DividingLine between items
- ActionButton for adding new items

**Example:** `OccasionListContent.kt`, `RecipientListContent.kt`

### View Screens
**Pattern:**
- AsyncLoad wrapper
- AddEditHeader with edit/delete actions
- DeleteConfirmationDialog
- LazyColumn with details
- ActionButton for related actions

**Example:** `ViewOccasionContent.kt`, `ViewRecipientContent.kt`

### Add/Edit Screens
**Pattern:**
- StandardHeader
- Form fields with consistent spacing
- SaveCancelButtons at bottom

**Example:** `AddEditOccasionContent.kt`, `AddEditRecipientContent.kt`

## Best Practices

### 1. Always Use Standard Components
- Don't create custom button rows - use `SaveCancelButtons`
- Don't create custom delete dialogs - use `DeleteConfirmationDialog`
- Don't hardcode headers - use `StandardHeader` or `AddEditHeader`

### 2. Use Design System Constants
- Never hardcode font sizes - use `Typography.*`
- Never hardcode spacing - use `Spacing.*`
- Never hardcode icon sizes - use `IconSize.*`

### 3. Follow Component Patterns
- Keep business logic in Component files
- Keep UI in Content files
- Use `loadOnResume` for initialization
- Use `onMain`/`onIO` for coroutines

### 4. Consistent Naming
- List screens: `*ListComponent.kt` / `*ListContent.kt`
- View screens: `View*Component.kt` / `View*Content.kt`
- Add/Edit screens: `AddEdit*Component.kt` / `AddEdit*Content.kt`

### 5. Navigation
- Always navigate through component methods, not inline
- Use `nav.pushToFront()` for forward navigation
- Use `nav.pop()` for back navigation
- Use `nav.bringToFront()` for replacing current screen

## Migration Guide

When updating existing screens:

1. **Replace button rows:**
   ```kotlin
   // Before
   Row(modifier = Modifier.padding(top = 5.dp).fillMaxWidth()) {
       Button(onClick = { component.save() }) { Text("Save") }
       Button(onClick = { component.cancel() }) { Text("Cancel") }
   }
   
   // After
   SaveCancelButtons(
       onSave = { component.save() },
       onCancel = { component.cancel() }
   )
   ```

2. **Replace delete dialogs:**
   ```kotlin
   // Before
   if (showDialog.value) {
       ConfirmationDialog(
           onDismissRequest = { showDialog.value = false },
           onConfirmation = { showDialog.value = false; component.delete() },
           dialogText = "Are you sure you want to delete ${item.name}?"
       )
   }
   
   // After
   DeleteConfirmationDialog(
       showDialog = showDialog,
       itemName = item.name,
       onConfirm = { component.delete() }
   )
   ```

3. **Replace hardcoded sizes:**
   ```kotlin
   // Before
   Text("Header", fontSize = 30.sp)
   Column(modifier = Modifier.padding(10.dp))
   
   // After
   Text("Header", fontSize = Typography.headerSize)
   Column(modifier = Modifier.padding(Spacing.screenPadding))
   ```

## Testing Considerations

When testing UI components:
- Test with different screen sizes
- Verify spacing is consistent
- Check that all buttons are accessible
- Ensure dialogs dismiss properly
- Verify navigation flows work correctly

## Future Enhancements

Potential improvements to consider:
- Dark mode support in theme
- Animation/transition patterns
- Error state handling patterns
- Loading skeleton patterns
- Empty state patterns
