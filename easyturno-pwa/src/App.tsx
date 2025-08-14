import Layout from './components/Layout';
import ShiftList from './components/ShiftList';
import ShiftForm from './components/ShiftForm';
import useShiftStore from './services/shiftStore';

function App() {
  const shifts = useShiftStore((state) => state.shifts);

  return (
    <Layout>
      <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
        <div>
          <ShiftForm />
        </div>
        <div>
          <ShiftList shifts={shifts} />
        </div>
      </div>
    </Layout>
  );
}

export default App;
