import React, { useState } from 'react';
import shiftService from '../services/shiftService';
import { RecurrenceFrequency, DayOfWeek } from '../types/types';

const ShiftForm: React.FC = () => {
  // Existing state
  const [title, setTitle] = useState('');
  const [start, setStart] = useState('');
  const [end, setEnd] = useState('');

  // State for recurrence
  const [isRecurring, setIsRecurring] = useState(false);
  const [frequency, setFrequency] = useState<RecurrenceFrequency>(RecurrenceFrequency.DAILY);
  const [interval, setInterval] = useState(1);
  const [weekdays, setWeekdays] = useState<DayOfWeek[]>([]);
  const [endDate, setEndDate] = useState('');

  const handleWeekdayChange = (day: DayOfWeek) => {
    setWeekdays((prev) =>
      prev.includes(day) ? prev.filter((d) => d !== day) : [...prev, day]
    );
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!title || !start || !end) {
      alert('Please fill all required fields');
      return;
    }

    let recurrenceRule;
    if (isRecurring) {
      if (!endDate) {
        alert('Please provide an end date for the recurring shift.');
        return;
      }
      recurrenceRule = {
        frequency,
        interval,
        endDate: new Date(endDate),
        ...(frequency === RecurrenceFrequency.WEEKLY && { weekdays }),
      };
    }

    await shiftService.create({
      title,
      startDateTime: new Date(start),
      endDateTime: new Date(end),
      color: 'blue', // default color for now
      metadata: {},
      ...(isRecurring && { recurrenceRule }),
    });

    // Clear form
    setTitle('');
    setStart('');
    setEnd('');
    setIsRecurring(false);
    setFrequency(RecurrenceFrequency.DAILY);
    setInterval(1);
    setWeekdays([]);
    setEndDate('');
  };

  return (
    <div>
      <h2 className="text-xl font-semibold mb-2">Add New Shift</h2>
      <form onSubmit={handleSubmit} className="space-y-4">
        {/* Basic Shift Fields */}
        <div>
          <label htmlFor="title" className="block">Title</label>
          <input type="text" id="title" className="border p-1 w-full" value={title} onChange={(e) => setTitle(e.target.value)} />
        </div>
        <div>
          <label htmlFor="start" className="block">Start Time</label>
          <input type="datetime-local" id="start" className="border p-1 w-full" value={start} onChange={(e) => setStart(e.target.value)} />
        </div>
        <div>
          <label htmlFor="end" className="block">End Time</label>
          <input type="datetime-local" id="end" className="border p-1 w-full" value={end} onChange={(e) => setEnd(e.target.value)} />
        </div>

        {/* Recurrence Toggle */}
        <div className="flex items-center">
          <input type="checkbox" id="isRecurring" checked={isRecurring} onChange={(e) => setIsRecurring(e.target.checked)} />
          <label htmlFor="isRecurring" className="ml-2">This is a recurring shift</label>
        </div>

        {/* Recurrence Options */}
        {isRecurring && (
          <div className="p-4 border rounded space-y-4">
            <h3 className="font-semibold">Recurrence Rule</h3>
            <div>
              <label htmlFor="frequency" className="block">Frequency</label>
              <select id="frequency" value={frequency} onChange={(e) => setFrequency(e.target.value as RecurrenceFrequency)} className="border p-1 w-full">
                <option value={RecurrenceFrequency.DAILY}>Daily</option>
                <option value={RecurrenceFrequency.WEEKLY}>Weekly</option>
              </select>
            </div>
            <div>
              <label htmlFor="interval" className="block">Interval</label>
              <input type="number" id="interval" min="1" className="border p-1 w-full" value={interval} onChange={(e) => setInterval(parseInt(e.target.value, 10))} />
            </div>
            {frequency === RecurrenceFrequency.WEEKLY && (
              <div>
                <label className="block">Repeat on</label>
                <div className="flex space-x-2">
                  {(['SU', 'MO', 'TU', 'WE', 'TH', 'FR', 'SA'] as DayOfWeek[]).map((day) => (
                    <button key={day} type="button" onClick={() => handleWeekdayChange(day)} className={`p-2 rounded ${weekdays.includes(day) ? 'bg-blue-500 text-white' : 'bg-gray-200'}`}>
                      {day}
                    </button>
                  ))}
                </div>
              </div>
            )}
            <div>
              <label htmlFor="endDate" className="block">End Date</label>
              <input type="date" id="endDate" className="border p-1 w-full" value={endDate} onChange={(e) => setEndDate(e.target.value)} />
            </div>
          </div>
        )}

        <button type="submit" className="bg-blue-500 text-white p-2 rounded w-full">Add Shift</button>
      </form>
    </div>
  );
};

export default ShiftForm;
