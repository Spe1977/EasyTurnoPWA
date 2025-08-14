import React, { useState } from 'react';
import shiftService from '../services/shiftService';

const ShiftForm: React.FC = () => {
  const [title, setTitle] = useState('');
  const [start, setStart] = useState('');
  const [end, setEnd] = useState('');

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!title || !start || !end) {
      alert('Please fill all fields');
      return;
    }

    await shiftService.create({
      title,
      startDateTime: new Date(start),
      endDateTime: new Date(end),
      color: 'blue', // default color for now
      metadata: {},
    });

    // Clear form
    setTitle('');
    setStart('');
    setEnd('');
  };

  return (
    <div>
      <h2 className="text-xl font-semibold mb-2">Add New Shift</h2>
      <form onSubmit={handleSubmit}>
        <div className="mb-2">
          <label htmlFor="title" className="block">Title</label>
          <input
            type="text"
            id="title"
            className="border p-1 w-full"
            value={title}
            onChange={(e) => setTitle(e.target.value)}
          />
        </div>
        <div className="mb-2">
          <label htmlFor="start" className="block">Start Time</label>
          <input
            type="datetime-local"
            id="start"
            className="border p-1 w-full"
            value={start}
            onChange={(e) => setStart(e.target.value)}
          />
        </div>
        <div className="mb-2">
          <label htmlFor="end" className="block">End Time</label>
          <input
            type="datetime-local"
            id="end"
            className="border p-1 w-full"
            value={end}
            onChange={(e) => setEnd(e.target.value)}
          />
        </div>
        <button type="submit" className="bg-blue-500 text-white p-2 rounded">Add Shift</button>
      </form>
    </div>
  );
};

export default ShiftForm;
