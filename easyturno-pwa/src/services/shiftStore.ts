import { create } from 'zustand';
import { Shift } from '../types/types';

interface ShiftState {
  shifts: Shift[];
  setShifts: (shifts: Shift[]) => void;
  addShift: (shift: Shift) => void;
  addShifts: (shifts: Shift[]) => void;
  updateShift: (shift: Shift) => void;
  deleteShift: (id: string) => void;
}

const useShiftStore = create<ShiftState>((set) => ({
  shifts: [
    // Some initial mock data for testing
    {
      id: '1',
      title: 'Morning Shift',
      startDateTime: new Date('2025-08-08T08:00:00'),
      endDateTime: new Date('2025-08-08T16:00:00'),
      color: 'blue',
      metadata: {},
    },
    {
      id: '2',
      title: 'Night Shift',
      startDateTime: new Date('2025-08-08T22:00:00'),
      endDateTime: new Date('2025-08-09T06:00:00'),
      color: 'purple',
      metadata: {},
    },
  ],
  setShifts: (shifts) => set({ shifts }),
  addShift: (shift) => set((state) => ({ shifts: [...state.shifts, shift] })),
  addShifts: (newShifts) => set((state) => ({ shifts: [...state.shifts, ...newShifts] })),
  updateShift: (updatedShift) =>
    set((state) => ({
      shifts: state.shifts.map((shift) =>
        shift.id === updatedShift.id ? updatedShift : shift
      ),
    })),
  deleteShift: (id) =>
    set((state) => ({
      shifts: state.shifts.filter((shift) => shift.id !== id),
    })),
}));

export default useShiftStore;
