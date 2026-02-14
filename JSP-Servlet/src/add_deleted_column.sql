-- ============================================================
-- MIGRATION SCRIPT: Add DELETED column to existing HR.ITEM table
-- ============================================================
-- Run this script if you have an existing HR.ITEM table without the DELETED column
-- This will add the DELETED column needed for soft delete functionality
-- ============================================================

-- Step 1: Add DELETED column to HR.ITEM table
ALTER TABLE HR.ITEM ADD DELETED NUMBER DEFAULT 0;

-- Step 2: Update existing records to set DELETED = 0 (not deleted)
-- This ensures all existing items are marked as not deleted
UPDATE HR.ITEM SET DELETED = 0 WHERE DELETED IS NULL;

-- Step 3: Commit the changes
COMMIT;

-- Step 4: Verify the column was added and check the data
SELECT * FROM HR.ITEM;

-- Expected result: All existing items should have DELETED = 0

