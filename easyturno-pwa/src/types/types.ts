export type ShiftColor = 'blue' | 'green' | 'red' | 'yellow' | 'purple' | 'orange' | 'grey' | 'black';

export interface ShiftMetadata {
  // Placeholder for future metadata properties
}

export enum RecurrenceFrequency {
  DAILY = 'DAILY',
  WEEKLY = 'WEEKLY',
}

export type DayOfWeek = 'SU' | 'MO' | 'TU' | 'WE' | 'TH' | 'FR' | 'SA';

export interface RecurrenceRule {
  frequency: RecurrenceFrequency;
  interval: number;
  weekdays?: DayOfWeek[];
  endDate?: Date;
}

export interface Shift {
  id: string;
  parentId?: string;
  title: string;
  startDateTime: Date;
  endDateTime: Date;
  color: ShiftColor;
  description?: string;
  location?: string;
  metadata: ShiftMetadata;
  recurrenceRule?: RecurrenceRule;
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
