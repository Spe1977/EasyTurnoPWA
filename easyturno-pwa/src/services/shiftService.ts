import { Shift, CreateShiftRequest, ShiftFilter, DeleteOptions, BatchResult } from '../types/types';
import useShiftStore from './shiftStore';

interface ShiftOperations {
  create(shift: CreateShiftRequest): Promise<Shift>;
  read(filter: ShiftFilter): Promise<Shift[]>;
  update(id: string, updates: Partial<Shift>): Promise<Shift>;
  delete(id: string, options: DeleteOptions): Promise<void>;
  bulkDelete(pattern: 'single' | 'series' | 'all'): Promise<BatchResult>;
}

const shiftService: ShiftOperations = {
  create: async (shiftData) => {
    const addShift = useShiftStore.getState().addShift;
    const newShift: Shift = {
      ...shiftData,
      id: new Date().toISOString(), // simple unique id
    };
    addShift(newShift);
    return newShift;
  },

  read: async (filter) => {
    // Filtering logic will be added later
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
    // For now, this is a placeholder.
    // In a real app, this would depend on the 'pattern' parameter.
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
