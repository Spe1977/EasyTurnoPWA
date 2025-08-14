import React from 'react';
import { Shift } from '../types/types';

interface ShiftListProps {
  shifts: Shift[];
}

const ShiftList: React.FC<ShiftListProps> = ({ shifts }) => {
  return (
    <div>
      <h2 className="text-xl font-semibold mb-2">Shifts</h2>
      {shifts.length === 0 ? (
        <p>No shifts scheduled.</p>
      ) : (
        <ul>
          {shifts.map((shift) => (
            <li key={shift.id} className="border p-2 mb-2 rounded">
              <h3 className="font-bold">{shift.title}</h3>
              <p>Start: {shift.startDateTime.toLocaleString()}</p>
              <p>End: {shift.endDateTime.toLocaleString()}</p>
            </li>
          ))}
        </ul>
      )}
    </div>
  );
};

export default ShiftList;
