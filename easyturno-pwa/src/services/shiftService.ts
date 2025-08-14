import { Shift, CreateShiftRequest, ShiftFilter, DeleteOptions, BatchResult, RecurrenceFrequency, DayOfWeek } from '../types/types';
import useShiftStore from './shiftStore';

// Helper function to generate recurring shifts
const _generateRecurringShifts = (parentShift: Shift): Shift[] => {
    if (!parentShift.recurrenceRule || !parentShift.recurrenceRule.endDate) {
        return [];
    }

    const generatedShifts: Shift[] = [];
    const { recurrenceRule } = parentShift;
    const { frequency, interval, endDate, weekdays } = recurrenceRule;

    let currentDate = new Date(parentShift.startDateTime);
    const shiftDuration = parentShift.endDateTime.getTime() - parentShift.startDateTime.getTime();

    const dayMap: { [key in DayOfWeek]: number } = { SU: 0, MO: 1, TU: 2, WE: 3, TH: 4, FR: 5, SA: 6 };

    // Move to the next day to start generating, to avoid duplicating the parent shift itself
    currentDate.setDate(currentDate.getDate() + 1);

    while (currentDate <= endDate) {
        let shouldCreateShift = false;

        if (frequency === RecurrenceFrequency.DAILY) {
            const diffTime = Math.abs(currentDate.getTime() - parentShift.startDateTime.getTime());
            const diffDays = Math.round(diffTime / (1000 * 60 * 60 * 24));
            if (diffDays % interval === 0) {
                shouldCreateShift = true;
            }
        } else if (frequency === RecurrenceFrequency.WEEKLY) {
            if (weekdays && weekdays.length > 0) {
                const currentDayOfWeek = currentDate.getDay();
                if (weekdays.some(d => dayMap[d] === currentDayOfWeek)) {
                    const startOfWeek = new Date(parentShift.startDateTime);
                    startOfWeek.setDate(startOfWeek.getDate() - startOfWeek.getDay()); // Find the Sunday of the start week

                    const currentStartOfWeek = new Date(currentDate);
                    currentStartOfWeek.setDate(currentStartOfWeek.getDate() - currentStartOfWeek.getDay()); // Find the Sunday of the current week

                    const weekDiff = Math.round((currentStartOfWeek.getTime() - startOfWeek.getTime()) / (1000 * 60 * 60 * 24 * 7));

                    if (weekDiff % interval === 0) {
                        shouldCreateShift = true;
                    }
                }
            }
        }

        if (shouldCreateShift) {
             const newStartDateTime = new Date(currentDate);
             const newEndDateTime = new Date(newStartDateTime.getTime() + shiftDuration);

             generatedShifts.push({
                 ...parentShift,
                 id: `${parentShift.id}-${generatedShifts.length + 1}`,
                 parentId: parentShift.id,
                 startDateTime: newStartDateTime,
                 endDateTime: newEndDateTime,
                 recurrenceRule: undefined,
             });
        }

        currentDate.setDate(currentDate.getDate() + 1);
    }

    return generatedShifts;
};


interface ShiftOperations {
  create(shift: CreateShiftRequest): Promise<Shift>;
  read(filter: ShiftFilter): Promise<Shift[]>;
  update(id: string, updates: Partial<Shift>): Promise<Shift>;
  delete(id: string, options: DeleteOptions): Promise<void>;
  bulkDelete(pattern: 'single' | 'series' | 'all'): Promise<BatchResult>;
}

const shiftService: ShiftOperations = {
  create: async (shiftData) => {
    const { addShift, addShifts } = useShiftStore.getState();

    const parentShift: Shift = {
      ...shiftData,
      id: new Date().toISOString(),
    };

    addShift(parentShift);

    if (parentShift.recurrenceRule) {
        const childShifts = _generateRecurringShifts(parentShift);
        if(childShifts.length > 0) {
            addShifts(childShifts);
        }
    }

    return parentShift;
  },

  read: async (filter) => {
    const shifts = useShiftStore.getState().shifts;
    return shifts;
  },

  update: async (id, updates) => {
    const { shifts, updateShift } = useShiftStore.getState();
    const shiftToUpdate = shifts.find((s) => s.id === id);
    if (!shiftToUpdate) {
      throw new Error('Shift not found');
    }
    const updatedShift = { ...shiftToUpdate, ...updates };
    updateShift(updatedShift);
    return updatedShift;
  },

  delete: async (id, options) => {
    const deleteShift = useShiftStore.getState().deleteShift;
    deleteShift(id);
  },

  bulkDelete: async (pattern) => {
    const { shifts, setShifts } = useShiftStore.getState();
    const originalCount = shifts.length;
    if (pattern === 'all') {
      setShifts([]);
      return { success: true, count: originalCount };
    }
    return { success: false, count: 0 };
  }
};

export default shiftService;
