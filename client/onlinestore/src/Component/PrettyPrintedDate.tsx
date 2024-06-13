interface DateOptions {
  weekday: "long" | "short";
  year: "numeric" | "2-digit";
  month: "long" | "short" | "numeric" | "2-digit";
  day: "numeric" | "2-digit";
}

export const PrettyPrintedDate = ({ dateString }: { dateString: string }) => {
  const date = new Date(dateString);
  const options: DateOptions = {
    weekday: "long",
    year: "numeric",
    month: "long",
    day: "numeric",
  };

  return <div>{date.toLocaleDateString("en-ID", options)}</div>;
};