import pt.pauloortolan.lists.LazyList;
import pt.pauloortolan.lists.PartitionedLazyList;
import pt.pauloortolan.lists.SimpleLazyList;

void main() {
    try {
        List<String> names = Files
                .lines(Path.of(getClass().getResource("names.txt").getPath()))
                .toList();

        LazyList<String> lazyList = new SimpleLazyList(names);

        while (!lazyList.isEmpty()) {
            String name = lazyList.head();
            System.out.println(name);
            lazyList = lazyList.tail();
        }

        PartitionedLazyList partitionedLazyList = new PartitionedLazyList(names, 10);

        int batchNumber = 1;
        while (!partitionedLazyList.isEmpty()) {
            IO.println("Batch #%s".formatted(batchNumber++));

            List<String> data = partitionedLazyList.head();
            for (String name : data) {
                IO.println("\t%s".formatted(name));
            }

            partitionedLazyList = (PartitionedLazyList) partitionedLazyList.tail();
        }

    } catch (IOException e) {
        throw new RuntimeException(e);
    }
}
