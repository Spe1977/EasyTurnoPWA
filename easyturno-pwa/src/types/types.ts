export type ShiftColor = 'blue' | 'green' | 'red' | 'yellow' | 'purple' | 'orange' | 'grey' | 'black';

export interface ShiftMetadata {
  // Placeholder for future metadata properties
}

export interface Shift {
  id: string;
  title: string;
  startDateTime: Date;
  endDateTime: Date;
  color: ShiftColor;
  description?: string;
  location?: string;
  metadata: ShiftMetadata;
}

export type CreateShiftRequest = Omit<Shift, 'id'>;

export interface ShiftFilter {
    // For now, no filters are defined.
}

export interface DeleteOptions {
    // For now, no delete options are defined.
}

export interface BatchResult {
    success: boolean;
    count: number;
}
